<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "npa";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

if(isset($_POST['search']))
{
    $valueToSearch = $_POST['valueToSearch'];
    // search in all table columns
	// using concat mysql function
	

	$sql = "SELECT `locations`.`location`, `radio`.`red`, `rate`.`rate1`, `comm`.`comment`, `rates3`.`rateone`, `rates3`.`ratetwo`, `rates3`.`ratethree`, `tenrate`.`tenrates`, `commenttwo`.`commtwo`, `names`.`name`, `names`.`email`, `names`.`contactno`
FROM `locations`  
	LEFT JOIN `radio` ON `radio`.`locid` = `locations`.`id` 
	LEFT JOIN `rate` ON `rate`.`locid` = `locations`.`id` 
	LEFT JOIN `comm` ON `comm`.`locid` = `locations`.`id` 
	LEFT JOIN `rates3` ON `rates3`.`locid` = `locations`.`id` 
	LEFT JOIN `tenrate` ON `tenrate`.`locid` = `locations`.`id` 
	LEFT JOIN `commenttwo` ON `commenttwo`.`locid` = `locations`.`id` 
	LEFT JOIN `names` ON `names`.`locid` = `locations`.`id` WHERE CONCAT(`location`) LIKE '%".$valueToSearch."%' Order by `locations`.`id` ASC";

   // $sql = "SELECT * FROM `locations` WHERE CONCAT(`location`) LIKE '%".$valueToSearch."%'";
	$query = mysqli_query($conn, $sql);
    
}

 elseif(isset($_POST['submit']))
{
    $from=date('Y-m-d',strtotime($_POST['from']));
	$to=date('Y-m-d',strtotime($_POST['to']));

	 $from = $from." 00:00:00";
	 $to = $to." 23:59:59";

	$sql = "SELECT `locations`.`location`, `radio`.`red`, `rate`.`rate1`, `comm`.`comment`, `rates3`.`rateone`, `rates3`.`ratetwo`, `rates3`.`ratethree`, `tenrate`.`tenrates`, `commenttwo`.`commtwo`, `names`.`name`, `names`.`email`, `names`.`contactno`
	FROM `locations`  
		LEFT JOIN `radio` ON `radio`.`locid` = `locations`.`id` 
		LEFT JOIN `rate` ON `rate`.`locid` = `locations`.`id` 
		LEFT JOIN `comm` ON `comm`.`locid` = `locations`.`id` 
		LEFT JOIN `rates3` ON `rates3`.`locid` = `locations`.`id` 
		LEFT JOIN `tenrate` ON `tenrate`.`locid` = `locations`.`id` 
		LEFT JOIN `commenttwo` ON `commenttwo`.`locid` = `locations`.`id` 
		LEFT JOIN `names` ON `names`.`locid` = `locations`.`id` where `sdate` BETWEEN '$from' and '$to'";

   // $sql = "SELECT * FROM `locations` WHERE CONCAT(`location`) LIKE '%".$valueToSearch."%'";
	$query = mysqli_query($conn, $sql);
	
	//var_dump($sql);
} 

else{


$sql = "SELECT `locations`.`location`, `radio`.`red`, `rate`.`rate1`, `comm`.`comment`, `rates3`.`rateone`, `rates3`.`ratetwo`, `rates3`.`ratethree`, `tenrate`.`tenrates`, `commenttwo`.`commtwo`, `names`.`name`, `names`.`email`, `names`.`contactno`
FROM `locations` 
	LEFT JOIN `radio` ON `radio`.`locid` = `locations`.`id` 
	LEFT JOIN `rate` ON `rate`.`locid` = `locations`.`id` 
	LEFT JOIN `comm` ON `comm`.`locid` = `locations`.`id` 
	LEFT JOIN `rates3` ON `rates3`.`locid` = `locations`.`id` 
	LEFT JOIN `tenrate` ON `tenrate`.`locid` = `locations`.`id` 
	LEFT JOIN `commenttwo` ON `commenttwo`.`locid` = `locations`.`id` 
	LEFT JOIN `names` ON `names`.`locid` = `locations`.`id` ORDER BY `locations`.`id` ASC";

}

$query = mysqli_query($conn, $sql);

if (!$query) {
	die ('SQL Error: ' . mysqli_error($conn));
}
?>




<html>
<head>
	<title>Android Feedback Report</title>
	<style type="text/css">
		body {
			font-size: 15px;
			color: #343d44;
			font-family: "segoe-ui", "open-sans", tahoma, arial;
			padding: 0;
			margin: 0;
		}
		table {
			margin: auto;
			font-family: "Lucida Sans Unicode", "Lucida Grande", "Segoe Ui";
			font-size: 10px;
		}

		h1 {
			margin: 25px auto 0;
			text-align: center;
			text-transform: uppercase;
			font-size: 17px;
		}

		table td {
			transition: all .5s;
		}
		
		/* Table */
		.data-table {
			border-collapse: collapse;
			font-size: 12px;
			min-width: 537px;
		}

		.data-table th, 
		.data-table td {
			border: 1px solid #e1edff;
			padding: 7px 17px;
		}
		.data-table caption {
			margin: 7px;
		}

		/* Table Header */
		.data-table thead th {
			background-color: #508abb;
			color: #FFFFFF;
			border-color: #6ea1cc !important;
			text-transform: uppercase;
		}

		/* Table Body */
		.data-table tbody td {
			color: #353535;
		}
		.data-table tbody td:first-child,
		.data-table tbody td:nth-child(4),
		.data-table tbody td:last-child {
			text-align: right;
		}

		.data-table tbody tr:nth-child(odd) td {
			background-color: #f4fbff;
		}
		.data-table tbody tr:hover td {
			background-color: #ffffa2;
			border-color: #ffff0f;
		}

		/* Table Footer */
		.data-table tfoot th {
			background-color: #e5f5ff;
			text-align: right;
		}
		.data-table tfoot th:first-child {
			text-align: left;
		}
		.data-table tbody td:empty
		{
			background-color: #ffcccc;
		}
	</style>
</head>
<body>

</form>
	 
	<h1>Data Summary Sheet</h1>




<table class="data-table">
		<caption class="title"> > Customer Feedback Details < </caption>

		
		<center>
		<form action="Report.php" method="POST" >
            <input type="text" name="valueToSearch" aling="center" placeholder="Branch To Search">
            <input type="submit" name="search" value="Search"><br><br>
</form>
<div>
	<form action="Report.php"  method="POST" >
		<label>From: </label><input type="date" name="from">
		<label>To: </label><input type="date" name="to">
		<input type="submit" value="submit" name="submit">
	</form>
</div>
</center>
		
			<thead>

		

			<tr>
			<th>Branch Name</th>
				<th>First Visit</th>
				<th>Your Total Experience</th>
				<th>Who Served You</th>
				<th>Friendless</th>
				<th>Helpfulness</th>
				<th>Product knowledge</th>
				<th>Recommended NORITAKE</th>
				<th>Comments or Feedback</th>
				<th>Name</th>
				<th>email</th>
				<th>Phone</th>

			</tr>
		</thead>
		<tbody>
		<?php
		
		while ($row = mysqli_fetch_array($query))
		{
			
			echo '<tr>
					<td>'.$row['location'].'</td> 
					<td>'.$row['red'].'</td> 
					<td>'.$row['rate1'].'</td>
					<td>'.$row['comment'].'</td>
					<td>'.$row['rateone'].'</td>
					<td>'.$row['ratetwo'].'</td>
					<td>'.$row['ratethree'].'</td>
					<td>'.$row['tenrates'].'</td>
					<td>'.$row['commtwo'].'</td> 
					<td>'.$row['name'].'</td>
					<td>'.$row['email'].'</td>
					<td>'.$row['contactno'].'</td>
				</tr>';
			
		}?>
		</tbody>
		
	</table>
</body>
</html>