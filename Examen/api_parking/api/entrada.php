<?php
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_entrada_salida = 0;
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $fecha_entrada = date('Y-m-d H:i:s');
    //$hora_entrada = $_REQUEST['hora_entrada'];
    $id_bahia = $_REQUEST['bahia'];
    $observaciones = $_REQUEST['observaciones'];
    $estado = 'INGRESO';
    $id_usuario = 1;

    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM entrada_salida WHERE chapa = :chapa AND estado ='INGRESO'");
        $stmt2->bindValue(":chapa", $chapa);
        $stmt2->execute();
        $entrada = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($entrada['chapa'])) {
            $message = "Ya se encuentra registrado una entrada con esa chapa = " . $chapa;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO entrada_salida (chapa, fecha_entrada,  id_bahia, observaciones, estado, id_usuario)
					VALUES (:chapa, :fecha_entrada,  :id_bahia, :observaciones, :estado, :id_usuario)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':chapa', $chapa);
            $stmt->bindValue(':fecha_entrada', $fecha_entrada);
            $stmt->bindValue(':id_bahia', $id_bahia);
            $stmt->bindValue(':observaciones', $observaciones);
            $stmt->bindValue(':estado', $estado);
            $stmt->bindValue(':id_usuario', $id_usuario);
            $result = $stmt->execute();
            $id_entrada_salida = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente la entrada" :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "selectBahia") {
    // funciona el select para la grilla
    $stmt = $dbconn->prepare('SELECT id_bahia, nom_bahia from bahias ORDER BY id_bahia ASC');
    $stmt->execute();
    $data = array();
    while ($bahias = $stmt->fetch(PDO::FETCH_OBJ)) {
        $data[] = array(
            "id_bahia" => $bahias->id_bahia,
            "nom_bahia" => $bahias->nom_bahia


        );
    }
    echo json_encode($data);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchBahia") {
    //terminado
    $bahia = $_REQUEST['bahia'];

    $sql_search = "SELECT id_bahia, nom_bahia from bahias where id_bahia=:id_bahia";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_bahia', $bahia);

    if ($stmt->execute()) {
        $a = 0;
        while ($bahias = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_bahia" => $bahias->id_bahia,
                "nom_bahia" => $bahias->nom_bahia
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchV") {
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
                "nom_tipvehiculo" => $vehiculo->nom_tipvehiculo,
                "nom_marca" => $vehiculo->nom_marca,
                "nombres" => $vehiculo->nombres
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchSalida") {
    //terminado
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $fecha_salida = date('Y-m-d H:i:s');

    $sql_search = "SELECT e.chapa,date_part('day' ,'$fecha_salida'-e.fecha_entrada) as dias,
    date_part('hours','$fecha_salida'-e.fecha_entrada) as horas , (c.cedula || ' / ' || c.nombre) AS nombres
   FROM entrada_salida e INNER JOIN vehiculo v ON e.chapa = v.chapa
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
                "dias" => $vehiculo->dias,
                "horas" => $vehiculo->horas,
                "nombres" => $vehiculo->nombres
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
} else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "update") {
    //terminado
    $result = 0;
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $fecha_salida = date('Y-m-d H:i:s');
    $tiempo_total = $_REQUEST['tiempo'];
    $estado = 'SALIO';

    $stmt2 = $dbconn->prepare("SELECT * FROM entrada_salida WHERE chapa = :chapa AND estado ='SALIO'");
    $stmt2->bindValue(":chapa", $chapa);
    $stmt2->execute();
    $entrada = $stmt2->fetch(PDO::FETCH_ASSOC);
    if (!empty($entrada['chapa'])) {
        $message = "Ya se encuentra registrado una salida con esa chapa = " . $chapa;
        $status = "error";
        print json_encode(array("status" => $status, "message" => $message));
        exit();
    } else {
        $sql = "UPDATE entrada_salida SET 
        fecha_salida= :fecha_salida,
        estado= :estado,     
        tiempo_total= :tiempo_total 
        WHERE chapa = :chapa AND estado ='INGRESO'";
        $stmt = $dbconn->prepare($sql);
        $stmt->bindValue(':chapa', $chapa);
        $stmt->bindValue(':fecha_salida', $fecha_salida);
        $stmt->bindValue(':tiempo_total', $tiempo_total);
        $stmt->bindValue(':estado', $estado);
        $result = $stmt->execute();
        $message = $result ? "Registro de la salida exitosamente!" :
            "Ocurrio un error intentado resolver la solicitud";
        $status = $result ? "success" : "error";
        print json_encode(array("status" => $status, "message" => $message));
    }
} else // FORM NOT SENT
{
    print json_encode(array("status" => "error", "message" => "Formulario no enviado"));
}
