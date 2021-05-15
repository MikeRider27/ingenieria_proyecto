<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_zona = 0;
    $nom_zona = $_REQUEST['nom_zona'];

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM zona WHERE nom_zona = :nom_zona");
        $stmt2->bindValue(":nom_zona", $nom_zona);
        $stmt2->execute();
        $zonas = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($zonas['nom_zona'])) {
            $message = "Ya se encuentra registrado una zona con esa descripcion = " . $nom_zona;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO zona (nom_zona)
					VALUES (:nom_zona)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':nom_zona', $nom_zona);
            $result = $stmt->execute();
            $id_zona = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente la zona con el id = " . $id_zona :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message, "id_zona" => $id_zona));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $id_zona = $_REQUEST['id_zona'];
    $nom_zona = $_REQUEST['nom_zona'];
    if (!empty($id_zona)) {
        $sql = 'UPDATE zona SET nom_zona= :nom_zona WHERE id_zona = :id_zona';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_zona', $id_zona);
    }
    $stmt->bindValue(':nom_zona', $nom_zona);
    $result = $stmt->execute();
    $message = $result ? "Registro de la zona modificado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $id_zona = $_REQUEST['id_zona'];
    if (!empty($id_zona)) {
        $sql = 'DELETE FROM zona WHERE id_zona = :id_zona';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_zona', $id_zona);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro de la zona borrado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT * FROM zona ORDER BY id_zona ASC');
    $stmt->execute();
    $data = array();
    while ($zona = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array("id_zona" => $zona->id_zona, "nom_zona" => $zona->nom_zona);
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $id_zona = $_REQUEST['id_zona'];

    $sql_search = "SELECT * from zona where id_zona=:id_zona";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_zona', $id_zona);

    if ($stmt->execute()) {
        $a = 0;
        while ($zona = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array("status" => "success", "id_zona" => $zona->id_zona, "nom_zona" => $zona->nom_zona);
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
