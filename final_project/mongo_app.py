import json
from pymongo import MongoClient
from flask import Flask, request, jsonify
import certifi
import os
import traceback

app = Flask(__name__)

password = "thomash123"
client = MongoClient(
    f'mongodb+srv://thahne:{password}@cisc349.movnd.mongodb.net/?retryWrites=true&w=majority&appName=CISC349',
    tlsCAFile=certifi.where()
)
db = client["Final"]

@app.route('/')
def index():
    return "<h1>Welcome to our server!!</h1>"

@app.route("/api/products", methods=["GET"])
def get_products():
    collection = db["products"]
    
    try:
        products = list(collection.find())
        
        for product in products:
            product['_id'] = str(product['_id'])
        
        return jsonify(products)
    
    except Exception as e:
        error_message = f"Error: {str(e)}\n{traceback.format_exc()}"
        print(error_message)
        return jsonify({'error': error_message}), 500

@app.route("/api/products", methods=["POST"])
def add_product():
    collection = db["products"]
    
    try:
        request_data = request.get_json()
        product_name = request_data['productName']
        price = request_data['price']
        description = request_data['description']

        product_metadata = {
            'productName': product_name,
            'price': price,
            'description': description
        }
        
        _id = collection.insert_one(product_metadata)

        return jsonify({'message': 'Product added successfully', 'id': str(_id.inserted_id)}), 201
    
    except Exception as e:
        error_message = f"Error: {str(e)}\n{traceback.format_exc()}"
        print(error_message)
        return jsonify({'error': error_message}), 500

@app.route("/api/login", methods=["POST"])
def admin_login():
    try:
        data = request.get_json()
        username = data.get("username")
        password = data.get("password")

        if not username or not password:
            return jsonify({"success": False, "message": "Username or password missing"}), 400

        user = db["login"].find_one({"username": username})

        if user:
            if password == user['password']:
                return jsonify({"success": True, "message": "Login successful"})
            else:
                return jsonify({"success": False, "message": "Invalid credentials"}), 401
        else:
            return jsonify({"success": False, "message": "User not found"}), 404

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":

    print(app.url_map)

    app.run(host="0.0.0.0", port=5100)