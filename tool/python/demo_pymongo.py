# -*- coding: utf-8 -*-
"""
    mongo
    ~~~~~~~~~~~~~~~~

    Created by ioniocn on 17/3/15

    :copyright: (c) 2017 by ioniocn.github.io
    :license: BSD, see LICENSE for more details.
"""

from pymongo import MongoClient
from bson.son import SON
import pprint
MONGO_URI='mongodb://localhost:27017/'
client = MongoClient(MONGO_URI)
db_book = client.book
col_zhifeng = db_book.zhifeng

pprint.pprint(col_zhifeng.find_one())

##
for item in col_zhifeng.find():
    pprint.pprint(item)

