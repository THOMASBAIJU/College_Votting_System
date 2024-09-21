from flask import *
from database import *
import uuid
from datetime import datetime

voter=Blueprint('voter',__name__)

@voter.route('/voterhome',methods=['get','post'])
def voterhome():
	return render_template("voter/voter_home.html")


@voter.route('/setpas',methods=['get','post'])
def setpas():
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
			return redirect(url_for("voter.setpas"))
	return render_template("voter/change_password.html")


@voter.route('/catview',methods=['get','post'])
def catview():
	data={}
	q="select * from category inner join election using(election_id)"
	res=select(q)
	data['viewcat']=res
	return render_template("voter/view_categories.html",data=data)



@voter.route('/candidview',methods=['get','post'])
def candidview():
	data={}
	q="select *,concat(fname,' ',lname)as name from candidates inner join student using(stud_id) inner join category using(cat_id)"
	res=select(q)
	data['viewcandid']=res
	return render_template("voter/view_candidates.html",data=data)



@voter.route('/electionstatus',methods=['get','post'])
def electionstatus():
	data={}
	q="select * from election"
	res=select(q)
	data['viewstat']=res
	return render_template("voter/check_election_status.html",data=data)



@voter.route('/makevote',methods=['get','post'])
def makevote():
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

	return render_template("voter/make_vote.html",data=data)


@voter.route('/viewresults',methods=['get','post'])
def viewresults():
	data={}
	q="select *,concat(fname,' ',lname)as name from results inner join category using(cat_id) inner join candidates using(candid_id) inner join student using(stud_id)"
	res=select(q)
	data['viewresults']=res
	return render_template("voter/view_results.html",data=data)

