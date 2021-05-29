<?php
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $id_tipvehiculo = $_REQUEST['id_tipvehiculo'];
    $id_marca = $_REQUEST['id_marca'];
    $id_cliente = $_REQUEST['id_cliente'];

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM vehiculo WHERE chapa = :chapa");
        $stmt2->bindValue(":chapa", $chapa);
        $stmt2->execute();
        $cliente = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($cliente['chapa'])) {
            $message = "Ya se encuentra registrado un vehiculo con esa chapa = " . $chapa;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO vehiculo (chapa, id_tipvehiculo, id_marca, id_cliente)
					VALUES (:chapa, :id_tipvehiculo, :id_marca, :id_cliente)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':chapa', $chapa);
            $stmt->bindValue(':id_tipvehiculo', $id_tipvehiculo);
            $stmt->bindValue(':id_marca', $id_marca);
            $stmt->bindValue(':id_cliente', $id_cliente);
            $result = $stmt->execute();
            $message = $result ? "Se registró correctamente el vehiculo con la chapa = " . $chapa :
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
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $id_tipvehiculo = $_REQUEST['id_tipvehiculo'];
    $id_marca = $_REQUEST['id_marca'];
    $id_cliente = $_REQUEST['id_cliente'];
    if (!empty($chapa)) {
        $sql = 'UPDATE vehiculo SET id_tipvehiculo= :id_tipvehiculo,
                                             id_marca= :id_marca, 
                                             id_cliente= :id_cliente WHERE chapa = :chapa';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':chapa', $chapa);
    }
    $stmt->bindValue(':id_tipvehiculo', $id_tipvehiculo);
    $stmt->bindValue(':id_marca', $id_marca);
    $stmt->bindValue(':id_cliente', $id_cliente);
    $result = $stmt->execute();
    $message = $result ? "Registro del tipo de vehiculo modificado exitosamente!" :
         "Ocurrio un error intentado resolver la solicitud";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "delete") {
    //terminado
    $result = 0;
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    if (!empty($chapa)) {
        $sql = 'DELETE FROM vehiculo WHERE chapa = :chapa';
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':chapa', $chapa);
    }
    $result = $stmt->execute();
    $message = $result ? "Registro del vehiculo borrado exitosamente!" :
     "Ocurrio un error intentado resolver la solicitud";
    $status = $result ? "success" : "error";
    print json_encode(array("status" => $status, "message" => $message));
} else if (isset($_GET['accion']) and $_GET['accion'] == "select") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare("SELECT v.chapa, v.id_tipvehiculo, t.nom_tipvehiculo, 
    v.id_marca, m.nom_marca, v.id_cliente, (c.cedula || ' / ' || c.nombre) AS nombres  
    FROM vehiculo v inner join tipo_vehiculo t ON v.id_tipvehiculo = t.id_tipvehiculo
    inner join marca m ON v.id_marca = m.id_marca
    inner join cliente c ON v.id_cliente = c.id_cliente ORDER BY v.chapa ASC");
    $stmt->execute();
    $data = array();
    while ($vehiculo = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "chapa" => $vehiculo->chapa,
            "nom_tipvehiculo" => $vehiculo->nom_tipvehiculo,
            "nom_marca" => $vehiculo->nom_marca,
            "nombres" => $vehiculo->nombres
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "search") {
    //terminado
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');

    $sql_search = "SELECT v.chapa, v.id_tipvehiculo, t.nom_tipvehiculo, 
    v.id_marca, m.nom_marca, v.id_cliente, (c.cedula || ' / ' || c.nombre) AS nombres  
    FROM vehiculo v inner join tipo_vehiculo t ON v.id_tipvehiculo = t.id_tipvehiculo
    inner join marca m ON v.id_marca = m.id_marca
    inner join cliente c ON v.id_cliente = c.id_cliente where v.chapa=:chapa";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':chapa', $chapa);

    if ($stmt->execute()) {
        $a = 0;
        while ($vehiculo = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "chapa" => $vehiculo->chapa,
                "id_tipvehiculo" => $vehiculo->id_tipvehiculo,
                "id_marca" => $vehiculo->id_marca,
                "id_cliente" => $vehiculo->id_cliente
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "selectMarca") {
    //terminado    
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
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchMarca") {
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
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "selectTipoV") {
    //terminado    
    $stmt = $dbconn->prepare('SELECT id_tipvehiculo, nom_tipvehiculo FROM tipo_vehiculo ORDER BY id_tipvehiculo ASC');
    $stmt->execute();
    $data = array();
    while ($tipovehiculo = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_tipvehiculo" => $tipovehiculo->id_tipvehiculo,
            "nom_tipvehiculo" => $tipovehiculo->nom_tipvehiculo
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchTipoV") {
    //terminado
    $id_tipvehiculo = $_REQUEST['id_tipvehiculo'];
    $sql_search = "SELECT id_tipvehiculo, nom_tipvehiculo from tipo_vehiculo where id_tipvehiculo=:id_tipvehiculo";
    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_tipvehiculo', $id_tipvehiculo);
    if ($stmt->execute()) {
        $a = 0;
        while ($tipovehiculo = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_tipvehiculo" => $tipovehiculo->id_tipvehiculo,
                "nom_tipvehiculo" => $tipovehiculo->nom_tipvehiculo
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "selectCliente") {
    //terminado    
    $stmt = $dbconn->prepare("SELECT id_cliente, (cedula || ' / ' || nombre) AS nombres FROM cliente ORDER BY id_cliente ASC");
    $stmt->execute();
    $data = array();
    while ($cliente = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_cliente" => $cliente->id_cliente,
            "nombres" => $cliente->nombres
        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchCliente") {
    //terminado
    $id_cliente = $_REQUEST['id_cliente'];
    $sql_search = "SELECT id_cliente, (cedula || ' / ' || nombre) AS nombres FROM cliente where id_cliente=:id_cliente";
    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_cliente', $id_cliente);
    if ($stmt->execute()) {
        $a = 0;
        while ($cliente = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_cliente" => $cliente->id_cliente,
                "nombres" => $cliente->nombres
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
