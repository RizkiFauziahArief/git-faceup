from flask import Flask, jsonify, request, session
from flask_sqlalchemy import SQLAlchemy
from werkzeug.security import generate_password_hash, check_password_hash
from datetime import datetime, timedelta
from dotenv import load_dotenv

from tensorflow import keras
load_dotenv()
model = keras.models.load_model('acne_model_v2.h5')

import jwt
import secrets
import string
import PIL.Image as Image
import numpy as np
import io
import base64
import os 

db = SQLAlchemy()

class User(db.Model):
    __tablename__ = "user"
    nama = db.Column(db.String(255), unique=True)
    email = db.Column(db.String(255), unique=True, primary_key=True)
    password = db.Column(db.String(255), nullable=False)

# buat class token
class Token(db.Model):
    __tablename__ = "token"
    email = db.Column(db.String(255))
    token = db.Column(db.String(255), unique=True, primary_key=True)
    
app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv("DB_URI")
app.config['SECRET_KEY'] = 'secret'

class_jerawat = { 
    0: "Blackhead",
    1: "Nodules",
    2: "Papule",
    3: "Pustules",
    4: "Whitehead" 
}
db.init_app(app)

with app.app_context():
    db.create_all()

# Mengatur konteks aplikasi
with app.app_context():
    # Kode lain di bawah ini
    connection = db.session()

@app.route('/')
def hello():
        return jsonify("FACEUP"), 200

@app.route("/register", methods=["POST"])
def RegisterUser():
    try:
        user_data = request.get_json()
        nama = user_data["nama"]
        email = user_data["email"]
        password = user_data["password"]

        hashed_password = generate_password_hash(password)

        user = User(nama=nama, email=email, password=hashed_password)
        db.session.add(user)
        db.session.commit()
        return jsonify({'error': False, 'message': 'your registration is successfull'}), 200
    except Exception as e:
        return jsonify({'error': True, "message": str(e)}), 400


@app.route("/login", methods=["POST"])
def login():
    try:
        email = request.json["email"]
        password = request.json["password"]

        user = User.query.filter_by(email=email).first()

        if user is None: 
            return jsonify({"error": True, "message": "Invalid email"}), 401

        if not check_password_hash(user.password, password):
            return jsonify({"error": True, "message": "Invalid password"}), 401

        token = jwt.encode({
            'username': email,
            'exp': datetime.utcnow() + timedelta(minutes=30)},  
            app.config['SECRET_KEY'],
            algorithm='HS256'
        )
        
        # masukin database
        token = Token(email=email, token=token)
        db.session.add(token)
        db.session.commit()
        session['email'] = email
        return jsonify({'error': False, 'message': 'Login success', 'nama': user.nama, 'email': user.email, 'token': token.token}), 200
    except :
        return jsonify({'error': True, 'message': 'Login invalid'}), 400


# ngecek token
@app.route("/protected", methods=["GET"])
def protected():
    token = request.headers.get("token")

    if not token:
        return jsonify({"error": True, "message": "Token is missing"}), 401

    try:
        decoded = jwt.decode(token, app.config['SECRET_KEY'], algorithms=["HS256"]) 
        username = decoded['username']

        return jsonify({"error": False, "message": "Authenticated"}), 200

    except jwt.ExpiredSignatureError:
        return jsonify({"error": True, "message": "Token has expired"}), 401
    except jwt.InvalidTokenError:
        return jsonify({"error": True, "message": "Invalid token"}), 401
    
@app.route('/predict', methods=['POST'])
def predict():
    # Get the input image file from the request
    file = request.files['image']

    # Read the image file
    img = Image.open(file)

    # Resize the image to the expected input shape of the model
    img = img.resize((150, 150))
    img = img.convert('RGB')

    # Preprocess the image (if needed)
    # ...

    # Convert image to numpy array
    img_array = np.array(img)

    # Expand dimensions to match the input shape expected by the model
    img_array = np.expand_dims(img_array, axis=0)

    # Perform prediction using the loaded model
    getPredict = model.predict(img_array)

    # Get the predicted class
    predicted_class = np.argmax(getPredict, axis=-1)
    predicted_class = int(predicted_class)
    class_get = class_jerawat.get(predicted_class)

    # Convert the numpy array to image
    img_result = Image.fromarray(np.uint8(img_array[0]))

    # Create an in-memory stream for the image file
    img_byte_arr = io.BytesIO()
    img_result.save(img_byte_arr, format='JPEG')
    img_byte_arr.seek(0)
    
    # Return the predicted class and image as response
    response = {
        'predicted_class': int(predicted_class),
        'class_jerawat' : class_get,
        'success': True,
        'message': 'Prediction successful'
    }
    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True, port=os.getenv("PORT"), host="0.0.0.0") 