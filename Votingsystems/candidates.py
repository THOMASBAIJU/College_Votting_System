from flask import *
from database import *
import uuid
from datetime import datetime

candidates=Blueprint('candidates',__name__)

@candidates.route('/candidatehome',methods=['get','post'])
def candidatehome():
	return render_template("candidates/candidates_home.html")


@candidates.route('/changepas',methods=['get','post'])
def changepas():
	if 'submit' in request.form:
		uname=request.form['uname']
		pas=request.form['pwd1']
		pas2=request.form['pwd2']

		q="select * from login where username='%s' and password='%s'"%(uname,pas)
		res=select(q)
		if res:
			q="update login set password='%s' where username='%s' and password='%s'"%(pas2,uname,pas)
			update(q)
			flash("password changed successfully")
			return redirect(url_for("candidates.changepas"))
	return render_template("candidates/set_password.html")



@candidates.route('/candidatecat',methods=['get','post'])
def candidatecat():
	data={}
	q="select * from category inner join election using(election_id)"
	res=select(q)
	data['viewcat']=res

	return render_template("candidates/view_categories.html",data=data)


@candidates.route('/applicationsub',methods=['get','post'])
def applicationsub():
	ids=request.args['ids']
	id=session['log_id']
	if 'submit' in request.form:
		des=request.form['des']

		q="insert into candidates values(null,(select stud_id from student where log_id='%s'),'%s','pending')"%(id,ids)
		cid=insert(q)
		q="insert into applications values(null,'%s','%s','pending','%s')"%(ids,cid,des)
		insert(q)
		flash("application submitted")

	return render_template("candidates/submit_application.html")

@candidates.route('/applistatus',methods=['get','post'])
def applistatus():
	data={}
	
	id=session['log_id']
	q="SELECT *,concat(fname,' ',lname)as name FROM applications INNER JOIN category USING(cat_id) INNER JOIN candidates USING(candid_id) INNER JOIN student USING(stud_id) WHERE stud_id=(SELECT stud_id FROM student WHERE log_id='%s')"%(id)
	res=select(q)
	data['applcns']=res
	return render_template("candidates/view_application_status.html",data=data)


@candidates.route('/candidatesview',methods=['get','post'])
def candidatesview():
	data={}
	q="select *,concat(fname,' ',lname)as name from candidates inner join student using(stud_id) inner join category using(cat_id)"
	res=select(q)
	data['viewcandid']=res
	return render_template("candidates/view_candidates.html",data=data)


@candidates.route('/votestatus',methods=['get','post'])
def votestatus():
	data={}
	q="select * from election"
	res=select(q)
	data['viewstat']=res
	return render_template("candidates/check_voting_status.html",data=data)


@candidates.route('/makevotes',methods=['get','post'])
def makevotes():
	data={}
	id=session['log_id']
	q="select * from category"
	res=select(q)
	data['viewcat']=res

	q="select *,concat(fname,' ',lname)as name from candidates inner join student using(stud_id)"
	res=select(q)
	data['viewcandid']=res

	if 'submit' in request.form:
		cat=request.form['cat']
		can=request.form['can']
		now=datetime.now()
		dt_string=now.strftime("%d/%m/%y %H:%M:%S")

		q="insert into voting values(null,(select stud_id from student where log_id='%s'),'%s','%s','%s')"%(id,cat,can,dt_string)
		insert(q)
		q="insert into voters values(null,(select stud_id from student where log_id='%s'))"%(id)
		insert(q)
		flash("vote making successfully")

	return render_template("candidates/make_votes.html",data=data)


@candidates.route('/resultsview',methods=['get','post'])
def resultsview():
	data={}
	q="select *,concat(fname,' ',lname)as name from results inner join category using(cat_id) inner join candidates using(candid_id) inner join student using(stud_id)"
	res=select(q)
	data['viewresults']=res

	return render_template("candidates/view_results.html",data=data)

@candidates.route('/sendcomplaint',methods=['get','post'])
def sendcomplaint():
	data={}
	id=session['log_id']
	if 'submit' in request.form:
		com=request.form['com']
		q="insert into complaints values(null,(SELECT candid_id FROM candidates INNER JOIN student USING(stud_id) WHERE stud_id=(SELECT stud_id FROM student WHERE log_id='%s')),'%s','pending',curdate())"%(id,com)
		insert(q) 
		flash("message send")

	q="select * from complaints where candid_id=(SELECT candid_id FROM candidates INNER JOIN student USING(stud_id) WHERE stud_id=(SELECT stud_id FROM student WHERE log_id='%s'))"%(id)
	res=select(q)
	data['viewcom']=res
	return render_template("candidates/send_complaint.html",data=data)

