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
mdb = client.book
mcl = mdb.mcl

pprint.pprint(mcl.find_one())

##
for item in mcl.find():
    pprint.pprint(item)

