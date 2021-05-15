<?php
session_start();
include('/core/connection.php');
$dbconn = getConnection();

/*
* LOGIN
*/

// Check if the form was sent and the action is LOGIN
if(isset($_GET['accion']) AND $_GET['accion'] == "ingresar")
{
	$nick = $_GET['nick'];
	$pass = sha1($_GET['pass']);

	// prepare statement for search user
	$stmt = $dbconn->prepare('SELECT * FROM usuario WHERE nick = :nick AND pass = :pass');
	// bind value to the :id parameter
	$stmt->bindValue(':nick', $nick);
	$stmt->bindValue(':pass', $pass);
	// execute the statement
	$stmt->execute();
	// return the result set as an object
	$user = $stmt->fetchObject();

	if($user != FALSE){	// Authorized access
		$_SESSION['id_usuario'] = $user->id;
		$_SESSION['nombre'] = $user->nombre;
		$_SESSION['email'] = $user->email;	
	}
	else // User not authorized
	{
		$response = array('status' => 'error');
	}
	print json_encode($response);
}
else // FORM NOT SENT
{
	print json_encode(array("status" => "error", "message" => "Formulario no enviado"));
}

?>