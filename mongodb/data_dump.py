#this file is used to dump the products.json data into a product_catalogue collection

import json
from pymongo import MongoClient 
from dotenv import load_dotenv
import os
 
load_dotenv()
 
# load env variables 
username = os.getenv('MONGO_INITDB_ROOT_USERNAME')
password = os.getenv('MONGO_INITDB_ROOT_PASSWORD')
port=os.getenv('MONGO_PORT')
host=os.getenv('MONGO_HOST')
db_name=os.getenv('MONGO_DB_NAME')

# making Connection
myclient = MongoClient("mongodb://"+host+":"+port+"/",username=username,password=password)

# database 
db = myclient[db_name]

# we're creating the product catalogue collection
Collection = db["product_catalogue"]
 
# opening the json file
with open('products.json') as file:
    file_data = json.load(file)


Collection.insert_many(file_data) 