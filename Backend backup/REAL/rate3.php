<?php
 
/*
* Database Constants
* Make sure you are putting the values according to your database here 
*/
define('DB_HOST','localhost');
define('DB_USERNAME','root');
define('DB_PASSWORD','');
define('DB_NAME', 'npa');
 
//Connecting to the database
$conn = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
 
//checking the successful connection
if($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
 
//making an array to store the response
$response = array(); 
 
//if there is a post request move ahead 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
 //getting the name from request 
 $name = $_POST['name']; 
 $email =$_POST['email'];
 $contactno =$_POST['contactno'];

 
 //creating a statement to insert to database 

 //$stmt = $conn->prepare("INSERT INTO names(name) VALUES (?)");
 //$stmt = $conn->prepare("INSERT INTO names(location) VALUES (?)");
 //$stmt = $conn->prepare("INSERT INTO names(email) VALUES (?)");



// $stmt = $conn->prepare("INSERT INTO names(name, location,email,contactno,comment) VALUES (?,?,?,?,?)");
$stmt = $conn->prepare("INSERT INTO rates3(rateone,ratetwo,ratethree) VALUES (?,?,?)");

 
 //binding the parameter to statement //"S mean" string
  $stmt->bind_param('sss', $name,$email,$contactno);
 //$stmt->bind_param('sssis', $name,$location,$email,$contactno,$comment);

 
 
 //if data inserts successfully
 if($stmt->execute()){
 //making success response 
 $response['error'] = false; 
 $response['message'] = 'Data saved successfully'; 
 }else{
 //if not making failure response 
 $response['error'] = true; 
 $response['message'] = 'Please try later';
 }
 
}else{
 $response['error'] = true; 
 $response['message'] = "Invalid request"; 
}
 
//displaying the data in json format 
echo json_encode($response);