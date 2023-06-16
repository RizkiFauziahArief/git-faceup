# Use the official Python image as the base image
FROM python:3.9-slim-buster

# Set the working directory in the container
WORKDIR /workspace

# Initial Repository PPA Update
RUN apt-get update -y

# Install Container Dependencies
RUN apt-get install unzip curl gpg lsb-release python3-dev default-libmysqlclient-dev build-essential python-mysqldb -y

# Copy the requirements file into the container
COPY requirements.txt .

# Copy the Flask app code into the container
COPY . .

# Install the required packages
RUN pip install --no-cache-dir -r requirements.txt

# Set the environment variable for the Flask app port
ENV PORT 8080

# # Expose the port on which the Flask app will run
EXPOSE $PORT

# Set the entry point for the container
CMD ["python","main.py"] 
