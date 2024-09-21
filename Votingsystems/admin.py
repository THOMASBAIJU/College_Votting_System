from flask import *
from database import *
import uuid
from Crypto import Random
from Crypto.Cipher import AES
import random
import binascii
import os
import base64
from CustomHash import *

admin=Blueprint('admin',__name__)

@admin.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template("admin/admin_home.html")


@admin.route('/category',methods=['get','post'])
def category():
	data={}
	q="select * from election"
	res=select(q)
	data['viewele']=res
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from category where cat_id='%s'"%(id)
		delete(q)

	if action=="update":
		q="select * from category where cat_id='%s'"%(id)
		res=select(q)
		data['updatedata']=res

		q="select election_id,titile,(election_id='%s') as eltitle from election order by eltitle desc,election_id asc"%(res[0]['election_id'])
		res1=select(q)
		data['viewele']=res1

	if 'update' in request.form:
		e=request.form['ele']
		c=request.form['cname']

		q="update category set election_id='%s',cat_name='%s' where cat_id='%s'"%(e,c,id)
		update(q)

	
	if 'submit' in request.form:
		e=request.form['ele']
		c=request.form['cname']
		q="insert into category values(null,'%s','%s')"%(e,c)
		insert(q)
		flash("category added")

	q="select * from category inner join election using(election_id)"
	res=select(q)
	data['viewcat']=res

	return render_template("admin/manage_categories.html",data=data)


@admin.route('/student',methods=['get','post'])
def student():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['ids']
	else:
		action=None
	if action=="delete":
		q="delete from student where stud_id='%s'"%(ids)
		delete(q)

	if action=="update":
		q="select * from student where stud_id='%s'"%(ids)
		res=select(q)
		data['updatedata']=res
	if 'update' in request.form:
		
		f=request.form['fname']
		l=request.form['lname']
		
		ph=request.form['phn']
		e=request.form['email']
		# sk=request.form['key']
		

		q="update student set fname='%s',lname='%s',phone='%s',email='%s' where stud_id='%s'"%(f,l,ph,e,ids)
		update(q)

		flash("updated successfully")
		return redirect(url_for("admin.student"))

	if 'submit' in request.form:
		r=request.form['rno']
		f=request.form['fname']
		l=request.form['lname']
		p=request.files['img']
		path='static/uploads/'+str(uuid.uuid4())+p.filename
		p.save(path)
		c=request.form['course']
		b=request.form['batch']
		ph=request.form['phn']
		e=request.form['email']
		
		typ=request.form['stud']
		pas=request.form['pass']
		
		q="insert into login values(null,'%s','%s','%s')"%(e,pas,typ)
		id=insert(q)

		q="insert into student values(null,'%s','%s','%s','%s','/%s','%s','%s','%s','%s','%s')"%(id,r,f,l,path,c,b,ph,e,len(data))
		insert(q)
		flash("record added successfully")
		return redirect(url_for("admin.student"))
	
	q="select * from student"
	res1=select(q)
	data['viewrecord']=res1

	return render_template("admin/manage_student_record.html",data=data)


@admin.route('/applications',methods=['get','post'])
def applications():
	data={}
	q="SELECT *,CONCAT(fname,' ',lname)AS name FROM applications INNER JOIN candidates USING(candid_id) INNER JOIN student USING(stud_id) INNER JOIN category ON applications.`cat_id`=category.`cat_id` "
	res=select(q)
	data['applcns']=res
	return render_template("admin/view_applications.html",data=data)


@admin.route('/accept',methods=['get','post'])
def accept():
	data={}
	id=request.args['id']
	ids=request.args['ids']
	q="update applications set appl_status='accept' where appl_id='%s'"%(id)
	update(q)
	q="update candidates set cand_status='candidate' where candid_id='%s' "%(ids)
	update(q)
	flash("accepted")
	return redirect(url_for("admin.applications"))
	return render_template("admin/view_applications.html",data=data)

@admin.route('/reject',methods=['get','post'])
def reject():
	data={}
	id=request.args['id']
	ids=request.args['ids']
	q="update applications set appl_status='reject' where appl_id='%s'"%(id)
	update(q)
	q="update candidates set cand_status='rejected' where candid_id='%s' "%(ids)
	update(q)
	flash("rejected")
	return redirect(url_for("admin.applications"))
	return render_template("admin/view_applications.html",data=data)



@admin.route('/finallist',methods=['get','post'])
def finallist():
	data={}
	q="SELECT *,CONCAT(fname,' ',lname)AS name FROM applications INNER JOIN candidates USING(candid_id) INNER JOIN student USING(stud_id) INNER JOIN category ON applications.`cat_id`=category.`cat_id` where appl_status='accept'"
	res=select(q)
	data['applcns']=res
	return render_template("admin/view_final_list.html",data=data)


@admin.route('/election',methods=['get','post'])
def election():
	data={}


	from datetime import date

	today = date.today()
	print("Today's date:", today)


	if 'submit' in request.form:
		e=request.form['ele']
		d=request.form['date']




		q="insert into election values(null,'%s','%s','not started')"%(e,d)
		insert(q)
		flash("inserted successfully")

	q="select * from election"
	res=select(q)
	data['viewstat']=res

	return render_template("admin/manage_election.html",data=data,today=today)



@admin.route('/start',methods=['get','post'])
def start():
	data={}
	id=request.args['id']
	q="update election set vot_status='start' where election_id='%s'"%(id)
	update(q)
	flash("election time started")
	return redirect(url_for("admin.election"))
	return render_template("admin/manage_election.html",data=data)



@admin.route('/finish',methods=['get','post'])
def finish():
	data={}
	id=request.args['id']
	q="update election set vot_status='finish' where election_id='%s'"%(id)
	update(q)
	flash("election time finished")
	return redirect(url_for("admin.election"))
	return render_template("admin/manage_election.html",data=data)


# @admin.route('/results',methods=['get','post'])
# def results():
# 	data={}
# 	q="select *,concat(fname,' ',lname)as name from candidates inner join student using(stud_id)"
# 	res=select(q)
# 	data['viewcandid']=res

# 	q="select * from category"
# 	res=select(q)
# 	data['viewcat']=res
	
# 	if 'submit' in request.form:
# 		cn=request.form['candid']
# 		c=request.form['cat']
# 		v=request.form['nvotes']

# 		q="insert into results values(null,'%s','%s','%s')"%(cn,c,v)
# 		insert(q)
# 		flash("Result published")

	
# 	return render_template("admin/publish_results.html",data=data)



@admin.route('/viewcomplaint',methods=['get','post'])
def viewcomplaint():
	data={}

	q="select *,concat(fname,' ',lname)as name from complaints inner join candidates using(candid_id) inner join student using(stud_id) where solution='pending'"
	res=select(q)
	data['viewcompaint']=res
	
	j=0
	for i in range(1,len(res)+1):
		
		if 'submit'+str(i) in request.form:
			solution=request.form['solution'+str(i)]
			print(res[j]['com_id'])
			q="update complaints set solution='%s' where com_id='%s'" %(solution,res[j]['com_id'])
			update(q)
			return redirect(url_for('admin.viewcomplaint')) 	
		j=j+1
	return render_template('admin/view_complaint.html', data=data)



@admin.route('/results',methods=['get','post'])
def results():
	data={}
	q="select *,concat(fname,' ',lname)as name from candidates inner join student using(stud_id)"
	res=select(q)
	data['viewcandid']=res

	# q="select * from candidates"
	# res=select(q)
	# data['viewcandid']=res

	q="select * from category"
	res=select(q)
	data['viewcat']=res


	if 'submit' in request.form:
		cn=request.form['candid']
		c=request.form['cat']

		q1="SELECT * FROM `results` WHERE `candid_id`='%s' AND `cat_id`='%s'"%(cn,c)
		rr=select(q1)
		if rr:
			flash("Already Result Published")
		else:
			qq="SELECT *,COUNT(`voting_id`) AS rvot FROM `voting` WHERE `candid_id`='%s' AND `cat_id`='%s'"%(cn,c)
			rv=select(qq)
			print(rv[0]['rvot'])
			if rv:	
				q="insert into results values(null,'%s','%s','%s')"%(cn,c,rv[0]['rvot'])
				insert(q)
				flash("Result published")

	
	return render_template("admin/publish_results.html",data=data)


@admin.route('/view_total_result',methods=['get','post'])
def view_total_result():
	data={}
	q="select * from category"
	res=select(q)
	data['viewcat']=res


	if 'submit' in request.form:
		cat=request.form['cat']
		q="SELECT * FROM results INNER JOIN `candidates` USING(candid_id) INNER JOIN `student` USING(stud_id) inner join category on category.cat_id=results.cat_id where results.cat_id='%s'"%(cat)
		res=select(q)
		data['results']=res

	# id=request.args['id']
	# q="update election set vot_status='finish' where election_id='%s'"%(id)
	# update(q)
	# flash("election time finished")
	# return redirect(url_for("admin.election"))
	return render_template("admin/view_total_result.html",data=data)


