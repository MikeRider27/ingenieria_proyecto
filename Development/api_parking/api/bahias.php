<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_bahia = 0;
    $nom_bahia = $_REQUEST['nom_bahia'];
    $id_tipbahia = $_REQUEST['id_tipbahia'];
    $id_zona = $_REQUEST['id_zona'];
    $estado_bahia = 'LIBRE';

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM bahias WHERE nom_bahia = :nom_bahia");
        $stmt2->bindValue(":nom_bahia", $nom_bahia);
        $stmt2->execute();
        $bahia = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($bahia['nom_bahia'])) {
            $message = "Ya se encuentra registrado una bahia con esa descripcion = " . $nom_bahia;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO bahias (nom_bahia, id_tipbahia, id_zona, estado_bahia)
					VALUES (:nom_bahia, :id_tipbahia, :id_zona, :estado_bahia)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':nom_bahia', $nom_bahia);
            $stmt->bindValue(':id_tipbahia', $id_tipbahia);
            $stmt->bindValue(':id_zona', $id_zona);
            $stmt->bindValue(':estado_bahia', $estado_bahia);

            $result = $stmt->execute();
            $id_bahia = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente la bahias con el id = " . $id_bahia :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message, "id_bahia" => $id_bahia));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $id_bahia = $_REQUEST['id_bahia'];
    $nom_bahia = $_REQUEST['nom_bahia'];
    $id_tipbahia = $_REQUEST['id_tipbahia'];
    $id_zona = $_REQUEST['id_zona'];
    // $estado_bahia = 'OCUPADO';
    if (!empty($id_bahia)) {
        $sql = 'UPDATE bahias SET nom_bahia= :nom_bahia,
                                  id_tipbahia= :id_tipbahia,
                                  id_zona= :id_zona WHERE id_bahia = :id_bahia';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_bahia', $id_bahia);
    }
    $stmt->bindValue(':nom_bahia', $nom_bahia);
    $stmt->bindValue(':id_tipbahia', $id_tipbahia);
    $stmt->bindValue(':id_zona', $id_zona);
    $result = $stmt->execute();
    $message = $result ? "Registro de la bahias modificado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $id_bahia = $_REQUEST['id_bahia'];
    if (!empty($id_bahia)) {
        $sql = 'DELETE FROM bahias WHERE id_bahia = :id_bahia';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':id_bahia', $id_bahia);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro de la bahias borrado exitosamente!" : "Ocurrio un error intentado resolver la solicitud, por favor complete todos los campos o recargue de vuelta la pagina";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT * FROM bahias ORDER BY id_bahia ASC');
    $stmt->execute();
    $data = array();
    while ($bahias = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_bahia" => $bahias->id_bahia,
            "nom_bahia" => $bahias->nom_bahia,
            "id_tipbahia" => $bahias->id_tipbahia,
            "id_zona" => $bahias->id_zona,
            "estado_bahia" => $bahias->estado_bahia
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $id_bahia = $_REQUEST['id_bahia'];

    $sql_search = "SELECT * from bahias where id_bahia=:id_bahia";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_bahia', $id_bahia);

    if ($stmt->execute()) {
        $a = 0;
        while ($bahias = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_bahia" => $bahias->id_bahia,
                "nom_bahia" => $bahias->nom_bahia,
                "id_tipbahia" => $bahias->id_tipbahia,
                "id_zona" => $bahias->id_zona,
                "estado_bahia" => $bahias->estado_bahia
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
