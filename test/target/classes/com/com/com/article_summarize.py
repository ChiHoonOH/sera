
# article_summarize.py

# coding: utf-8

# In[1]:
# python 3 : pip install newspaper3k
# python 2 : pip install newspaper
# JPype1 설치: pip install jpype1 / https://www.lfd.uci.edu/~gohlke/pythonlibs/#jpype
# KoNLPy 설치: pip install konlpy
#  pip install scikit-learn

from newspaper import Article
from konlpy.tag import Kkma
from konlpy.tag import Twitter
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.preprocessing import normalize
import numpy as np


# In[2]:


class SentenceTokenizer(object):
    def __init__(self):
        self.kkma = Kkma()
        self.twitter = Twitter()
        self.stopwords = ['중인' ,'만큼', '마찬가지', '꼬집었', "연합뉴스", "데일리", "동아일보", "중앙일보", "조선일보", "기자"
,"아", "휴", "아이구", "아이쿠", "아이고", "어", "나", "우리", "저희", "따라", "의해", "을", "를", "에", "의", "가",]
    def url2sentences(self, url):
        article = Article(url, language='ko')
        article.download()
        article.parse()
        sentences = self.kkma.sentences(article.text)

        for idx in range(0, len(sentences)):
            if len(sentences[idx]) <= 10:
                sentences[idx-1] += (' ' + sentences[idx])
                sentences[idx] = ''

        return sentences

    def text2sentences(self, text):
        sentences = self.kkma.sentences(text)
        for idx in range(0, len(sentences)):
            if len(sentences[idx]) <= 10:
                sentences[idx-1] += (' ' + sentences[idx])
                sentences[idx] = ''

        return sentences

    def get_nouns(self, sentences):
        nouns = []
        for sentence in sentences:
            if sentence is not '':
                nouns.append(' '.join([noun for noun in self.twitter.nouns(str(sentence))
                                        if noun not in self.stopwords and len(noun) > 1]))

        return nouns


# In[3]:


class GraphMatrix(object):
        def __init__(self):
            self.tfidf = TfidfVectorizer()
            self.cnt_vec = CountVectorizer()
            self.graph_sentence = []

        def build_sent_graph(self, sentence):
            tfidf_mat = self.tfidf.fit_transform(sentence).toarray()
            self.graph_sentence = np.dot(tfidf_mat, tfidf_mat.T)
            return self.graph_sentence

        def build_words_graph(self, sentence):
            cnt_vec_mat = normalize(self.cnt_vec.fit_transform(sentence).toarray().astype(float), axis=0)
            vocab = self.cnt_vec.vocabulary_
            return np.dot(cnt_vec_mat.T, cnt_vec_mat), {vocab[word] : word for word in vocab}


# In[4]:


class Rank(object):
    def get_ranks(self, graph, d=0.85): # d = damping factor
        A = graph
        matrix_size = A.shape[0]
        for id in range(matrix_size):
            A[id, id] = 0 # diagonal 부분을 0으로
            link_sum = np.sum(A[:,id]) # A[:, id] = A[:][id]
            if link_sum != 0:
                A[:, id] /= link_sum
            A[:, id] *= -d
            A[id, id] = 1

        B = (1-d) * np.ones((matrix_size, 1))
        ranks = np.linalg.solve(A, B) # 연립방정식 Ax = b
        return {idx: r[0] for idx, r in enumerate(ranks)}


# In[5]:


class TextRank(object):
    def __init__(self, text):
        self.sent_tokenize = SentenceTokenizer()

        if text[:5] in ('http:', 'https'):
            self.sentences = self.sent_tokenize.url2sentences(text)
        else:
            self.sentences = self.sent_tokenize.text2sentences(text)

        self.nouns = self.sent_tokenize.get_nouns(self.sentences)

        self.graph_matrix = GraphMatrix()
        self.sent_graph = self.graph_matrix.build_sent_graph(self.nouns)
        self.words_graph, self.idx2word = self.graph_matrix.build_words_graph(self.nouns)

        self.rank = Rank()
        self.sent_rank_idx = self.rank.get_ranks(self.sent_graph)
        self.sorted_sent_rank_idx = sorted(self.sent_rank_idx, key=lambda k: self.sent_rank_idx[k], reverse=True)

        self.word_rank_idx = self.rank.get_ranks(self.words_graph)
        self.sorted_word_rank_idx = sorted(self.word_rank_idx, key=lambda k: self.word_rank_idx[k], reverse=True)

    def summarize(self, sent_num=3):
        summary = []
        index=[]
        for idx in self.sorted_sent_rank_idx[:sent_num]:
            index.append(idx)

        index.sort()
        for idx in index:
            summary.append(self.sentences[idx])

        return summary

    def keywords(self, word_num=10):
        rank = Rank()
        rank_idx = rank.get_ranks(self.words_graph)
        sorted_rank_idx = sorted(rank_idx, key=lambda k: rank_idx[k], reverse=True)

        keywords = []
        index=[]
        for idx in sorted_rank_idx[:word_num]:
            index.append(idx)

        #index.sort()
        for idx in index:
            keywords.append(self.idx2word[idx])

        return keywords


# In[27]:
# font 문제 해결 
# https://lovit.github.io/nlp/2018/04/17/word_cloud/
font_path = 'NanumFont_TTF_ALL/NanumGothic.ttf'


# In[9]:

from wordcloud import WordCloud, STOPWORDS
import matplotlib.pyplot as plt
from PIL import Image
# %matplotlib inline 설정을 해주어야지만 노트북 안에 그래프가 디스플레이 된다.
#get_ipython().run_line_magic('matplotlib', 'inline')
from wordcloud import WordCloud, STOPWORDS
import matplotlib.pyplot as plt
from PIL import Image
import os
# %matplotlib inline #설정을 해주어야지만 노트북 안에 그래프가 디스플레이 된다.


# 그림 저장 함수
def save_fig(fig_id, tight_layout=True, fig_extension="png", resolution=300): 
    path = os.path.join(IMAGES_PATH, fig_id + "." + fig_extension) 
    print("Saving figure", fig_id) 
    if tight_layout: 
        plt.tight_layout() 
    plt.savefig(path, format=fig_extension, dpi=resolution)

IMAGES_PATH="C:\\my_STS\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ex00-1\\img"

def displayWordCloud(data = None, backgroundcolor = 'white', width=800, height=600, name='00000000'):
    wordcloud = WordCloud(stopwords = STOPWORDS, 
                          font_path = font_path,
                          background_color = backgroundcolor, 
                         width = width, height = height).generate(data)
    plt.figure(figsize = (15 , 10))
    plt.imshow(wordcloud)
    plt.axis("off")
    save_fig(name)
    #plt.show()

# In[6]:

'''
url = 'http://v.media.daum.net/v/20170611192209012?rcmd=r'
textrank = TextRank(url)
for row in textrank.summarize(3):
    print(row)
    print()
print('keywords :',textrank.keywords())


# In[9]:


url = 'https://sports.news.naver.com/kbaseball/news/read.nhn?oid=076&aid=0003299478'
textrank = TextRank(url)
for row in textrank.summarize(3):
    print(row)
    print()
print('keywords :',textrank.keywords())


# In[11]:


url = 'https://entertain.naver.com/read?oid=108&aid=0002719961'
textrank = TextRank(url)
for row in textrank.summarize(3):
    print(row)
    print()
print('keywords :',textrank.keywords())


# In[11]:


url = 'https://news.naver.com/main/read.nhn?oid=055&sid1=102&aid=0000666350&mid=shm&mode=LSD&nh=20180812211920'
textrank = TextRank(url)
for row in textrank.summarize(4):
    print(row)
    print()
print('keywords :',textrank.keywords())


# In[10]:


textrank.keywords()
' '.join(textrank.keywords())
'''


# In[38]:
'''

url = 'https://news.v.daum.net/v/20180813113526270?rcmd=rn'
textrank = TextRank(url)
for row in textrank.summarize(2):
    print(row)
    print()
print('keywords :',textrank.keywords())
get_ipython().run_line_magic('time', "displayWordCloud(' '.join(textrank.keywords()))")


# In[39]:


url = 'https://news.v.daum.net/v/20180813120007223'
textrank = TextRank(url)
for row in textrank.summarize(2):
    print(row)
    print()
print('keywords :',textrank.keywords())
get_ipython().run_line_magic('time', "displayWordCloud(' '.join(textrank.keywords()))")


# In[40]:


url = 'https://m.news.naver.com/read.nhn?mode=LSD&sid1=100&sid2=265&oid=421&aid=0003531955'
textrank = TextRank(url)
for row in textrank.summarize(2):
    print(row)
    print()
print('keywords :',textrank.keywords())
get_ipython().run_line_magic('time', "displayWordCloud(' '.join(textrank.keywords()))")

'''