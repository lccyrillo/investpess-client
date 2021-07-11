package com.cyrillo.investpess.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerMethodDefinition;
import proto.ativo.ativoobjetoproto.*;

public class InvestpassClient {
    public static void main(String[] args) {
        System.out.println("Hello I am Client");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",50051).
                usePlaintext().
                build();
        //System.out.println("Creating a Stub");
        AtivoServerServiceGrpc.AtivoServerServiceBlockingStub client = AtivoServerServiceGrpc.newBlockingStub(channel);

        AtivoObjeto ativoObjeto = AtivoObjeto.newBuilder().
                setTipoAtivo(1).
                setDescricaoCnpjAtivo("60.872.5----001-23").
                setNomeAtivo("ITAU UNIBANCO HOLDING S.A.").
                setSiglaAtivo("ITUB4").
                build();

        CadastraAtivoObjetoRequest request = CadastraAtivoObjetoRequest.newBuilder().
                setAtivo(ativoObjeto).
                build();

        CadastraAtivoObjetoResponse response = client.cadastraAtivoObjeto(request);

        System.out.println(response.getResponseCode() + " - " + response.getResponseMessage());


        ativoObjeto = AtivoObjeto.newBuilder().
                setTipoAtivo(1).
                setDescricaoCnpjAtivo("60.872.504/0001-23").
                setNomeAtivo("ITAU UNIBANCO HOLDING S.A.").
                setSiglaAtivo("ITUB4").
                build();

        request = CadastraAtivoObjetoRequest.newBuilder().
                setAtivo(ativoObjeto).
                build();

        response = client.cadastraAtivoObjeto(request);

        System.out.println(response.getResponseCode() + " - " + response.getResponseMessage());


        ativoObjeto = AtivoObjeto.newBuilder().
                setTipoAtivo(1).
                setDescricaoCnpjAtivo("00.000.000/0001-91").
                setNomeAtivo("BANCO DO BRASIL S.A").
                setSiglaAtivo("BBAS3").
                build();

        request = CadastraAtivoObjetoRequest.newBuilder().
                setAtivo(ativoObjeto).
                build();

        response = client.cadastraAtivoObjeto(request);

        System.out.println(response.getResponseCode() + " - " + response.getResponseMessage());




        ConsultaListaAtivoRequest request2 = ConsultaListaAtivoRequest.newBuilder()
                .setTipoAtivo(1)
                .build();
        ConsultaListaAtivoResponse response2 = client.consultaListaAtivo(request2);
        String resultadoProcessamento = "Resultado Processamento: " + response2.getResponseCode() + " - " + response2.getResponseMessage();
        System.out.println(resultadoProcessamento);
        // faz loop pela lista de ativos
        int totalItens = response2.getAtivosCount();

        for (int i =0; i <totalItens; i++) {
            System.out.println(response2.getAtivos(i).getSiglaAtivo() + " - " + response2.getAtivos(i).getNomeAtivo());
        }
        System.out.println("Shutting down de client");
        channel.shutdown();

    }
}