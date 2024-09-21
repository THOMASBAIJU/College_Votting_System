from flask import *
from database import *
import uuid
from Crypto import Random
from Crypto.Cipher import AES
import random
import binascii
import os
import base64
import dataduplication as dd
from CustomHash import *
from samp import *


public=Blueprint('public',__name__)

@public.route('/',methods=['get','post'])
def pubhome():
	return render_template("public/index.html")


@public.route('/login',methods=['get','post'])
def login():
	if 'submit' in request.form:
		uname=request.form['uname']
		pas=request.form['pwd']

		q="select * from login where username='%s' and password='%s'"%(uname,pas)
		res=select(q)

		if res:
			
			session['log_id']=res[0]['log_id']
			if res[0]['usertype']=="admin":
				return redirect(url_for("admin.adminhome"))
			
		else:
			flash("invalid username and password")


	return render_template("public/login.html")

@public.route('/otherlogin',methods=['get','post'])
def otherlogin():
	if 'submit' in request.form:
		key="hellloooooooooo"
		uname=request.form['uname']
		pas=request.form['pass']

		q="select * from login where username='%s' and password='%s'" %(uname,pas)
		res=select(q)
		if res:
			session['log_id']=res[0]['log_id']

			# if res[0]['usertype']=="voter":
			# 	return redirect(url_for("voter.voterhome"))
			if res[0]['usertype']=="candidate":

				
				return redirect(url_for("candidates.candidatehome"))
			
			else:
				flash("invalid username or password")

		else:
			flash("invalid username or password")


	return render_template("public/otherlogin.html")
