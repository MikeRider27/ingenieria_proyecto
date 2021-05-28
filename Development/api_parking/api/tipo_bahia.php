<?php
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_tipbahia = 0;
    $nom_tipbahia = $_REQUEST['nom_tipbahia'];

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM tipo_bahias WHERE nom_tipbahia = :nom_tipbahia");
        $stmt2->bindValue(":nom_tipbahia", $nom_tipbahia);
        $stmt2->execute();
        $cliente = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($cliente['nom_tipbahia'])) {
            $message = "Ya se encuentra registrado un tipo de bahia con esa descripcion = " . $nom_tipbahia;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO tipo_bahias (nom_tipbahia)
					VALUES (:nom_tipbahia)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':nom_tipbahia', $nom_tipbahia);
            $result = $stmt->execute();
            $id_tipbahia = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente la tipo_bahias con el id = " . $id_tipbahia :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message, "id_tipbahia" => $id_tipbahia));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $id_tipbahia = $_REQUEST['id_tipbahia'];
    $nom_tipbahia = $_REQUEST['nom_tipbahia'];
    if (!empty($id_tipbahia)) {
        $sql = 'UPDATE tipo_bahias SET nom_tipbahia= :nom_tipbahia WHERE id_tipbahia = :id_tipbahia';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_tipbahia', $id_tipbahia);
    }
    $stmt->bindValue(':nom_tipbahia', $nom_tipbahia);
    $result = $stmt->execute();
    $message = $result ? "Registro de la tipo_bahias modificado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $id_tipbahia = $_REQUEST['id_tipbahia'];
    if (!empty($id_tipbahia)) {
        $sql = 'DELETE FROM tipo_bahias WHERE id_tipbahia = :id_tipbahia';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_tipbahia', $id_tipbahia);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro de la tipo_bahias borrado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT * FROM tipo_bahias ORDER BY id_tipbahia ASC');
    $stmt->execute();
    $data = array();
    while ($tipo_bahias = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array("id_tipbahia" => $tipo_bahias->id_tipbahia, "nom_tipbahia" => $tipo_bahias->nom_tipbahia);
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $id_tipbahia = $_REQUEST['id_tipbahia'];

    $sql_search = "SELECT * from tipo_bahias where id_tipbahia=:id_tipbahia";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_tipbahia', $id_tipbahia);

    if ($stmt->execute()) {
        $a = 0;
        while ($tipo_bahias = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array("status" => "success", "id_tipbahia" => $tipo_bahias->id_tipbahia, "nom_tipbahia" => $tipo_bahias->nom_tipbahia);
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
