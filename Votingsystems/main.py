from flask import *
from database import *
from public import public
from admin import admin
from api import api
from candidates import candidates


app=Flask(__name__)
app.secret_key="qwertyu"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(api,url_prefix='/api')
app.register_blueprint(candidates,url_prefix='/candidates')


app.run(debug=True, port=5045,host="0.0.0.0")