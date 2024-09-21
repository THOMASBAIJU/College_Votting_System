import os
from CustomHash import encrypt,decrypt,create_key
from flask import current_app as app
from werkzeug.utils import secure_filename

import hashlib
from database import *
import base64
import  uuid
from shutil import copyfile
from datetime import datetime,timedelta

def create_tag(data):
    tag = hashlib.sha512(hashlib.sha512(data.encode("ascii")).hexdigest().encode("ascii")).hexdigest()
    return tag

def upload(data,name,desc):
    # print(data)
    unique = str(uuid.uuid4())
    filename =  "static/uploads" + "/" + secure_filename(unique + "." + name.split(".")[-1])

    key = None
    # key = str(create_key())
    # enc_text = encrypt(data,key)
    key = "heloooooo"
    enc_text = encrypt(data,key)

    fh = open(filename, "wb")
    fh.write(base64.b64decode(enc_text))
    fh.close()

    
    
    created = True
    return True

# def restore(file_id,emp_id):
#     q = "delete from ownership where file_id='%s' and emp_id='%s'" % (file_id,emp_id)
#     db.delete(q)
#     q = "select * from duplicates where file_id='%s'and emp_id='%s'" % (file_id,emp_id)
#     result = db.select(q)
#     filename = result[0]['dup_filename']

#     with open(filename, "rb") as imageFile:
#         data = base64.b64encode(imageFile.read()).decode('utf-8')

#     delete_file(filename)
#     q = "delete from duplicates where dup_filename='%s' and emp_id='%s'" % (filename,emp_id)
#     db.delete(q)
#     q = "select * from file where file_id='%s'" % file_id
#     result = db.select(q)
#     key = result[0]['key']
#     # data = decrypt(data,key)
#     filename = result[0]['filename']
#     return upload(data,filename,emp_id)

def download(file_id):
    key = "heloooooo"
    print(file_id)
    # q = "select * from downloadedfile where dfileid = '%s'" %(file_id)
    # res = db.select(q)
    # key = res[0]['key']
    # file = open(res[0]['file_path'],"r")
    with open(file_id, "rb") as imageFile:
        print("Eguybh")
        data = base64.b64encode(imageFile.read()).decode('utf-8')
    # data= file.read()
    print("DFvfv",data)
    data = decrypt(data,key)
    
    return data, os.path.basename(res[0]['filepath'])

def del_all_exp_files():
    date = (datetime.today()  - timedelta(days=7)).strftime('%Y-%m-%d')
    q = "select * from file inner join duplicates using(file_id) where date < '%s'" % date
    res = db.select(q)
    ids = []
    for row in res:
        ids.append(str(row['dup_id']))
        delete_file(row['dup_filename'])

    q = "delete from duplicates where dup_id in %s" % ("("+",".join(ids)+")")
    return db.update(q)

def get_all_exp_files():
    date = (datetime.today()  - timedelta(days=7)).strftime('%Y-%m-%d')
    q = "select * from file inner join duplicates using(file_id) where date < '%s'" % date
    return db.select(q)


def delete(file_id):
    q = "select * from  file where file_id='%s'" % file_id
    res = db.select(q)
    file_path = res[0]['file_path']
    delete_file(file_path)
    q = "delete from file where file_id='%s'" % file_id
    db.delete(q)

def delete_file(filename):
    if os.path.exists(filename):
        os.remove(filename)