<?php
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_caja = 0;
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $fecha_pago = date('Y-m-d H:i:s');  
    $id_entrada_salida = $_REQUEST['id_entrada_salida'];
    $monto = $_REQUEST['monto'];
    $tipo_pago = $_REQUEST['tipo_pago'];
    $estado = 'INGRESO';
    $id_usuario = 1;
 


    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM entrada_salida 
        WHERE id_entrada_salida = :id_entrada_salida AND estado ='INGRESO'");
        $stmt2->bindValue(":id_entrada_salida", $id_entrada_salida);
        $stmt2->execute();
        $entrada = $stmt2->fetch(PDO::FETCH_ASSOC);
        if (!empty($entrada['id_entrada_salida'])) {
            $message = "Todavia no se registro la salida de ese Ticket = " . $id_entrada_salida;
            $status = "error";
            print json_encode(array("status" => $status, "message" => $message));
            exit();
        } else {
            $sql = 'INSERT INTO caja_cobros (id_usuario, id_entrada_salida, monto, id_cliente,  fecha_pago)
					VALUES (:id_usuario, :id_entrada_salida, :monto, :id_cliente, :fecha_pago)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':id_usuario', $id_usuario);
            $stmt->bindValue(':id_entrada_salida', $id_entrada_salida);
            $stmt->bindValue(':monto', $monto);
            $stmt->bindValue(':id_cliente', $id_cliente);        
            $stmt->bindValue(':fecha_pago', $fecha_pago);         
            $result = $stmt->execute();
            $id_caja = $dbconn->lastInsertId();
            $message = $result ? "Se registró correctamente la entrada" :
                "Ocurrio un error intentado resolver la solicitud";
            $status = $result ? "success" : "error";
            print json_encode(array("status" => $status, "message" => $message));
        }
    } catch (Exception $e) {
        $result = FALSE;
        var_dump($e->getMessage());
    }
}else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchcliente") {
    //terminado
    $cedula = $_REQUEST['cedula'];

    $sql_search = "SELECT * from cliente where cedula=:cedula";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':cedula', $cedula);

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
}else if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "searchEntrada") {
    //terminado
    $id_entrada_salida = $_REQUEST['entrada'];

    $sql_search = "SELECT e.id_entrada_salida,e.chapa,e.tiempo_total,
    CASE WHEN e.tiempo_total::numeric > 24 THEN t.tarifa_dia::numeric*e.tiempo_total::numeric 
    ELSE t.tarifa_hora::numeric*e.tiempo_total::numeric END AS monto
      FROM entrada_salida e INNER JOIN vehiculo v ON e.chapa = v.chapa
      inner join cliente c ON v.id_cliente = c.id_cliente
      INNER join tipo_vehiculo t ON v.id_tipvehiculo = t.id_tipvehiculo WHERE e.id_entrada_salida=:id_entrada_salida";

    $stmt = $dbconn->prepare($sql_search);
    //poner numero de cantidad de campos por tabla diferente cantidad 
    $stmt->bindParam(':id_entrada_salida', $id_entrada_salida);

    if ($stmt->execute()) {
        $a = 0;
        while ($ticket = $stmt->fetch(PDO::FETCH_OBJ)) {
            $datos = array(
                "status" => "success",
                "id_entrada_salida" => $ticket->id_entrada_salida,
                "chapa" => $ticket->chapa,
                "monto" => $ticket->monto,
                "tiempo_total" => $ticket->tiempo_total
            );
            $a = 1;
        }

        if ($a == 0) $datos = array("status" => "NO EXISTE");
    } else {
        $datos = array("status" => "ERROR SQL");
    }

    echo json_encode($datos);
}   else // FORM NOT SENT
{
    print json_encode(array("status" => "error", "message" => "Formulario no enviado"));
}
