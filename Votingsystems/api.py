from flask import *
import demjson
from database import *
import uuid
import base64
from datetime import datetime

api=Blueprint("api",__name__)

@api.route('/login',methods=['get','post'])
def login():
	data={}
	data['method']='login'
	uname=request.args['username']
	print(uname)
	passs=request.args['password']
	
	q="SELECT * FROM login WHERE username='%s' AND password='%s'" %(uname,passs)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	return demjson.encode(data)



@api.route('/viewcategory/')
def viewcategory():
	data={}
	q="select * from category inner join election using(election_id)"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='viewcategory'
	return demjson.encode(data)

@api.route('/viewcandidate/')
def viewcandidate():
	data={}
	catid=request.args['vcatid']
	q="select *,concat(fname,' ',lname)as name from candidates inner join student using(stud_id) inner join category using(cat_id) where candidates.cat_id='%s'" %(catid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='viewcandidate'
	return demjson.encode(data)

@api.route('/viewresult/')
def viewresult():
	data={}
	q="select *,concat(fname,' ',lname)as name from results inner join category using(cat_id) inner join candidates using(candid_id) inner join student using(stud_id)"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='viewresult'
	return demjson.encode(data)

@api.route('/viewelection/')
def viewelection():
	data={}
	q="select * from election"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='viewelection'
	return demjson.encode(data)

@api.route('/viewcategorys/')
def viewcategorys():
	data={}
	eleid=request.args['eleid']
	q="select * from category inner join election using(election_id) where category.election_id='%s'" %(eleid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='viewcategorys'
	return demjson.encode(data)

@api.route('/viewcandidates/')
def viewcandidates():
	data={}
	catid=request.args['catid']
	q="select *,concat(fname,' ',lname)as name from candidates inner join student using(stud_id) inner join category using(cat_id) where candidates.cat_id='%s'" %(catid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='viewcandidates'
	return demjson.encode(data)




@api.route('/vote/')
def vote():
	data={}
	data['method']='vote'
	catid=request.args['catid']
	candid=request.args['candid']
	lid=request.args['lid']
	now=datetime.now()
	dt_string=now.strftime("%d/%m/%y %H:%M:%S")
	q="select * from voting where cat_id='%s' and stud_id=(select stud_id from student where log_id='%s')" %(catid,lid)
	res=select(q)
	if res:
		data['status']='voted'
	else:
		q="insert into voting values(null,(select stud_id from student where log_id='%s'),'%s','%s','%s')"%(lid,catid,candid,dt_string)
		print(q)
		insert(q)
		q="insert into voters values(null,(select stud_id from student where log_id='%s'))"%(lid)
		print(q)
		insert(q)
		data['status']='success'
	return demjson.encode(data)



