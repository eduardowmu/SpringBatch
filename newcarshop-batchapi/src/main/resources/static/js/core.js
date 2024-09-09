$(document).ready(function(){

    clearMessages();
    var carros = [];
    loadCarros();
  });

  function clearMessages(){
    $('#alert-success').hide();
    $('#alert-fail').hide();
    $('#alert-unexpected-fail').hide();
  }

  function executarJob(){

    $.get('http://localhost:8080/api/batch', function(mensagem, status){

        clearMessages();

        if('COMPLETED' === mensagem){
            $('#alert-success').show();
        } else if('FAILED' === mensagem){
            $('#alert-fail').show();
        }else{
            $('#alert-unexpected-fail').show();
        }
    });
  }

  function loadCarros(){

    $('#table-carros').find('tbody').each(function(){ this.remove();});

    $.get('http://localhost:8080/api/carros', function(result, status){
        
         carros = result;

         $(carros).each(
             
            function(){
              
                var $linha = $('<tr>');
                    var $id = $('<td>');
                    var $renavam = $('<td>');
                    var $marca = $('<td>');
                    var $modelo = $('<td>');
                    var $anoFabricacao = $('<td>');
                    var $anoModelo = $('<td>');
                    var $valorCompra = $('<td>');
                    var $valorVenda = $('<td>');
                    var $percentalMaxDesconto = $('<td>');
                    var $loja = $('<td>');
                    var $dataCadastro = $('<td>');

                $id.append(this.id);
                $renavam.append(this.renavam);
                $marca.append(this.marca);
                $modelo.append(this.modelo);
                $anoFabricacao.append(this.anoFabricacao);
                $anoModelo.append(this.anoModelo);
                $valorCompra.append(this.valorCompra);
                $valorVenda.append(this.valorVenda);
                $percentalMaxDesconto.append(this.percentalMaxDesconto);
                $loja.append(this.loja);
                $dataCadastro.append(this.dataCadastro);

                $linha.append($id);
                $linha.append($renavam);
                $linha.append($marca);
                $linha.append($modelo);
                $linha.append($anoFabricacao);
                $linha.append($anoModelo);
                $linha.append($valorCompra);
                $linha.append($valorVenda);
                $linha.append($percentalMaxDesconto);
                $linha.append($loja);
                $linha.append($dataCadastro);

                $tableCarros = $('#table-carros');
                $tableCarros.append($linha);
            })
    });
}