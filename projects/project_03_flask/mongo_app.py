import json
import base64
from pymongo import MongoClient
from flask import Flask, request, jsonify, send_from_directory
import certifi
import os
from datetime import datetime
import traceback

app = Flask(__name__)

# MongoDB connection setup
password = "thomash123"
client = MongoClient(
    f'mongodb+srv://thahne:{password}@cisc349.movnd.mongodb.net/?retryWrites=true&w=majority&appName=CISC349',
    tlsCAFile=certifi.where()
)
db = client["CISC349"]

UPLOAD_FOLDER = 'images'
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)

@app.route('/')
def index():
    return "<h1>Welcome to our server!!</h1>"

@app.route("/images", methods=["POST"])
def image_save():
    collection = db["images"]
    
    try:
        # Get JSON data from the request
        request_data = request.get_json()
        image_name = request_data['image_name']
        comment = request_data['comment']
        date_time = request_data['date_time']  # Consider converting this to a datetime object
        image_data = request_data['image']

        # Define image path and save it
        image_path = os.path.join(UPLOAD_FOLDER, image_name + ".jpg")
        with open(image_path, "wb") as img_file:
            img_file.write(base64.b64decode(image_data))
        
        # Prepare the metadata for MongoDB
        image_metadata = {
            'image_name': image_name,
            'comment': comment,
            'date_time': date_time,  # Ensure this is in the correct format, potentially a datetime object
            'image_path': image_path
        }
        
        # Insert metadata into MongoDB
        _id = collection.insert_one(image_metadata)
        
        return jsonify({'message': 'Image uploaded successfully', 'id': str(_id.inserted_id)})
    
    except Exception as e:
        # Log the error for debugging
        error_message = f"Error: {str(e)}\n{traceback.format_exc()}"
        print(error_message)  # This will print the error to your terminal
        return jsonify({'error': error_message}), 500

@app.route('/images', methods=['GET'])
def image_list():
    collection = db["images"]
    
    try:
        # Fetch all images from the collection
        images = list(collection.find())
        
        # Convert ObjectId to string before returning as JSON
        for image in images:
            image['_id'] = str(image['_id'])
        
        print(f"Got {len(images)} images.")
        return jsonify(images)  # Using jsonify will automatically handle serialization
    except Exception as e:
        # Log the error for debugging
        error_message = f"Error: {str(e)}\n{traceback.format_exc()}"
        print(error_message)  # This will print the error to your terminal
        return jsonify({'error': error_message}), 500

@app.route('/images/<filename>')
def get_image(filename):
    return send_from_directory('images', filename)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5100)