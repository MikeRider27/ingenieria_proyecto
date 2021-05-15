<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_marca = 0;
    $nom_marca = $_REQUEST['nom_marca'];

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM marca WHERE nom_marca = :nom_marca");
        $stmt2->bindValue(":nom_marca", $nom_marca);
        $stmt2->execute();
        $marca = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($marca['nom_marca'])) {
            $message = "Ya se encuentra registrado una marca con esa descripcion = " . $nom_marca;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO marca (nom_marca)
					VALUES (:nom_marca)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':nom_marca', $nom_marca);
            $result = $stmt->execute();
            $id_marca = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente la marca con el id = " . $id_marca :
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
    $id_marca = $_REQUEST['id_marca'];
    $nom_marca = $_REQUEST['nom_marca'];
    if (!empty($id_marca)) {
        $sql = 'UPDATE marca SET nom_marca= :nom_marca WHERE id_marca = :id_marca';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_marca', $id_marca);
    }
    $stmt->bindValue(':nom_marca', $nom_marca);
    $result = $stmt->execute();
    $message = $result ? "Registro de la marca modificado exitosamente!" :
        "Ocurrio un error intentado resolver la solicitud";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $id_marca = $_REQUEST['id_marca'];
    if (!empty($id_marca)) {
        $sql = 'DELETE FROM marca WHERE id_marca = :id_marca';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_marca', $id_marca);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro de la marca borrado exitosamente!" :
        "Ocurrio un error intentado resolver la solicitud";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT * FROM marca ORDER BY id_marca ASC');
    $stmt->execute();
    $data = array();
    while ($marca = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_marca" => $marca->id_marca,
            "nom_marca" => $marca->nom_marca
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $id_marca = $_REQUEST['id_marca'];
    $sql_search = "SELECT * from marca where id_marca=:id_marca";
    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_marca', $id_marca);
    if ($stmt->execute()) {
        $a = 0;
        while ($marca = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_marca" => $marca->id_marca,
                "nom_marca" => $marca->nom_marca
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
