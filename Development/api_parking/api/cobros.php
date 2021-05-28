<?php
session_start();
include('../core/connection.php');
$dbconn = getConnection();

// Check if the form was sent and the action is SAVE
if (isset($_REQUEST['accion']) and $_REQUEST['accion'] == "insert") {
    //terminado
    $result = 0;
    $id_caja = 0;
    $chapa = mb_strtoupper(trim($_REQUEST['chapa']), 'UTF-8');
    $fecha_pago = $_REQUEST['fecha_pago'];  
    $id_entrada_salida = $_REQUEST['id_entrada_salida'];
    $monto = $_REQUEST['monto'];
    $tipo_pago = $_REQUEST['tipo_pago'];
    $estado = 'INGRESO';
    $id_usuario = $_REQUEST['id_usuario'];
 


    try {
        $stmt2 = $dbconn->prepare("SELECT * FROM caja_cobros 
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
            $sql = 'INSERT INTO caja_cobros (id_usuario, id_entrada_salida, monto, id_cliente, tipo_pago, fecha_pago)
					VALUES (:id_usuario, :id_entrada_salida, :monto, :id_cliente, :tipo_pago, :fecha_pago)';
            $stmt = $dbconn->prepare($sql);
            $stmt->bindValue(':id_usuario', $id_usuario);
            $stmt->bindValue(':id_entrada_salida', $id_entrada_salida);
            $stmt->bindValue(':monto', $monto);
            $stmt->bindValue(':id_cliente', $id_cliente);
            $stmt->bindValue(':tipo_pago', $tipo_pago);
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
}  else // FORM NOT SENT
{
    print json_encode(array("status" => "error", "message" => "Formulario no enviado"));
}
