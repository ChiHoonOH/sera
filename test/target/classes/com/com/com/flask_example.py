from flask import Flask, request

import article_summarize
import word2vec_0821
from article_summarize import TextRank
from article_summarize import displayWordCloud
from word2vec_0821 import TitleSimilarity

import base64
import sys
from newspaper import Article
from konlpy.tag import Twitter
import datetime
 



app = Flask(__name__)


# 페이지
@app.route("/")
def hello():
    return "Hello Flask!"

@app.route("/article", methods=['POST'])
def article() :
    url = request.form['url']
    url = str(base64.b64decode(url), encoding='utf-8')
    print(url)

    article = Article(url, language='ko')
    article.download()
    article.parse()

    # 타이틀 생성 입력란
    result = url + '\n' +article.title + '\n'
    
    # 요약 (3줄)
    textrank = TextRank(article.text)
    for row in textrank.summarize(3):
        result += row
        result += '\n'
    # 이미지 이름 생성
    now = datetime.datetime.now()
    nowDatetime = now.strftime('%Y%m%d%H%M%S')
    n = nowDatetime  # 2015-04-19_12:11:32
    #keyword 추가
    displayWordCloud(data=' '.join(textrank.keywords()) ,name=n)
    result += n+'.png\n'
    for keyword in textrank.keywords() :
        result += (keyword+'\n')
    #print(result)

    #word2vec 분석결과 넣기
    titlesimilar = TitleSimilarity(article.title, article.text)
    x, y = titlesimilar.getTitleSimilarity()
    pos, neg = titlesimilar.getPositive()

    result += (str(x)+'\n' + str(y) +'\n' + str(pos)+'\n' + str(neg) +'\n')

    return result 
    
@app.route("/morphs", methods=['POST'])
def morphs() :
    url = request.form['url']
    url = str(base64.b64decode(url), encoding='utf-8')
    print(url)

    article = Article(url, language='ko')
    article.download()
    article.parse()

    m = Twitter()
    result = ""
    for s in m.pos(article.title):
        result += (s[0]+","+s[1])
        result += (" ")
    result +=("\n")


    # 본문 형태소
    for s in m.pos(article.text):
        result += (s[0]+","+s[1])
        result += (" ")
    result += ("\n")

    # 타이틀 생성 입력란
    return result

if __name__ == "__main__" :
    app.run()