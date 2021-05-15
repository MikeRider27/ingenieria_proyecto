<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_cliente = 0;
    $cedula = trim($_REQUEST['cedula']);
    $nombre = mb_strtoupper(trim($_REQUEST['nombre']), 'UTF-8');
    $celular = $_REQUEST['celular'];
    $direccion = $_REQUEST['direccion'];
    $email = $_REQUEST['email'];

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM cliente WHERE cedula = :cedula");
        $stmt2->bindValue(":cedula", $cedula);
        $stmt2->execute();
        $cliente = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($cliente['cedula'])) {
            $message = "Ya se encuentra registrado un cliente con esa cedula = " . $cedula;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO cliente (cedula, nombre, celular, direccion, email)
					VALUES (:cedula, :nombre, :celular, :direccion, :email)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':cedula', $cedula);
            $stmt->bindValue(':nombre', $nombre);
            $stmt->bindValue(':celular', $celular);
            $stmt->bindValue(':direccion', $direccion);
            $stmt->bindValue(':email', $email);
            $result = $stmt->execute();
            $id_cliente = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente el cliente con la cedula = " . $cedula :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $id_cliente = $_REQUEST['id_cliente'];
    $cedula = trim($_REQUEST['cedula']);
    $nombre = mb_strtoupper(trim($_REQUEST['nombre']), 'UTF-8');
    $celular = $_REQUEST['celular'];
    $direccion = $_REQUEST['direccion'];
    $email = $_REQUEST['email'];
    if (!empty($id_cliente)) {
        $sql = 'UPDATE cliente SET cedula= :cedula,
                                             nombre= :nombre, 
                                             celular= :celular,
                                             direccion= :direccion,
                                             email= :email WHERE id_cliente = :id_cliente';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_cliente', $id_cliente);
    }
    $stmt->bindValue(':cedula', $cedula);
    $stmt->bindValue(':nombre', $nombre);
    $stmt->bindValue(':celular', $celular);
    $stmt->bindValue(':direccion', $direccion);
    $stmt->bindValue(':email', $email);
    $result = $stmt->execute();
    $message = $result ? "Registro del tipo de vehiculo modificado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $id_cliente = $_REQUEST['id_cliente'];
    if (!empty($id_cliente)) {
        $sql = 'DELETE FROM cliente WHERE id_cliente = :id_cliente';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_cliente', $id_cliente);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro del tipo de vehiculo borrado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT * FROM cliente ORDER BY id_cliente ASC');
    $stmt->execute();
    $data = array();
    while ($cliente = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_cliente" => $cliente->id_cliente,
            "cedula" => $cliente->cedula,
            "nombre" => $cliente->nombre,
            "celular" => $cliente->celular,
            "direccion" => $cliente->direccion,
            "email" => $cliente->email
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $id_cliente = $_REQUEST['id_cliente'];

    $sql_search = "SELECT * from cliente where id_cliente=:id_cliente";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_cliente', $id_cliente);

    if ($stmt->execute()) {
        $a = 0;
        while ($cliente = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_cliente" => $cliente->id_cliente,
                "cedula" => $cliente->cedula,
                "nombre" => $cliente->nombre,
                "celular" => $cliente->celular,
                "direccion" => $cliente->direccion,
                "email" => $cliente->email
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
} else // FORM NOT SENT
{
    print json_encode(array("status" => "error", "message" => "Formulario no enviado"));
}
