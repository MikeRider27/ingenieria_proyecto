<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_tipvehiculo = 0;
    $nom_tipvehiculo = $_REQUEST['nom_tipvehiculo'];
    $tarifa_hora = $_REQUEST['tarifa_hora'];
    $tarifa_dia = $_REQUEST['tarifa_dia'];

    try {
        $sql = 'INSERT INTO tipo_vehiculo (nom_tipvehiculo, tarifa_hora, tarifa_dia)
					VALUES (:nom_tipvehiculo, :tarifa_hora, :tarifa_dia)';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':nom_tipvehiculo', $nom_tipvehiculo);
        $stmt->bindValue(':tarifa_hora', $tarifa_hora);
        $stmt->bindValue(':tarifa_dia', $tarifa_dia);
        $result = $stmt->execute();
        $id_tipvehiculo = $dbconn->lastInsertId();
        $message = $result ? "Se registró correctamente el tipo de vehiculo con el id = " . $id_tipvehiculo :
            "Ocurrio un error intentado resolver la solicitud";
        $status = $result ? "success" : "error";
        print json_encode(array("status" => $status, "message" => $message, "id_tipvehiculo" => $id_tipvehiculo));
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $id_tipvehiculo = $_REQUEST['id_tipvehiculo'];
    $nom_tipvehiculo = $_REQUEST['nom_tipvehiculo'];
    $tarifa_hora = $_REQUEST['tarifa_hora'];
    $tarifa_dia = $_REQUEST['tarifa_dia'];
    if (!empty($id_tipvehiculo)) {
        $sql = 'UPDATE tipo_vehiculo SET nom_tipvehiculo= :nom_tipvehiculo,
                                             tarifa_hora= :tarifa_hora, 
                                             tarifa_dia= :tarifa_dia WHERE id_tipvehiculo = :id_tipvehiculo';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_tipvehiculo', $id_tipvehiculo);
    }
    $stmt->bindValue(':nom_tipvehiculo', $nom_tipvehiculo);
    $stmt->bindValue(':tarifa_hora', $tarifa_hora);
    $stmt->bindValue(':tarifa_dia', $tarifa_dia);
    $result = $stmt->execute();
    $message = $result ? "Registro del tipo de vehiculo modificado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $id_tipvehiculo = $_REQUEST['id_tipvehiculo'];
    if (!empty($id_tipvehiculo)) {
        $sql = 'DELETE FROM tipo_vehiculo WHERE id_tipvehiculo = :id_tipvehiculo';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_tipvehiculo', $id_tipvehiculo);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro del tipo de vehiculo borrado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT * FROM tipo_vehiculo ORDER BY id_tipvehiculo ASC');
    $stmt->execute();
    $data = array();
    while ($tipo_vehiculo = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_tipvehiculo" => $tipo_vehiculo->id_tipvehiculo,
            "nom_tipvehiculo" => $tipo_vehiculo->nom_tipvehiculo,
            "tarifa_hora" => $tipo_vehiculo->tarifa_hora,
            "tarifa_dia" => $tipo_vehiculo->tarifa_dia
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $id_tipvehiculo = $_REQUEST['id_tipvehiculo'];

    $sql_search = "SELECT * from tipo_vehiculo where id_tipvehiculo=:id_tipvehiculo";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_tipvehiculo', $id_tipvehiculo);

    if ($stmt->execute()) {
        $a = 0;
        while ($tipo_vehiculo = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_tipvehiculo" => $tipo_vehiculo->id_tipvehiculo,
                "nom_tipvehiculo" => $tipo_vehiculo->nom_tipvehiculo,
                "tarifa_hora" => $tipo_vehiculo->tarifa_hora,
                "tarifa_dia" => $tipo_vehiculo->tarifa_dia
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
