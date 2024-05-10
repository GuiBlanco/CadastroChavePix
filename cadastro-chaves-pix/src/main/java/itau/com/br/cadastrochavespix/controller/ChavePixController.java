package itau.com.br.cadastrochavespix.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import itau.com.br.cadastrochavespix.dto.ChavePixAlteracaoDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixConsultaDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixDTO;
import itau.com.br.cadastrochavespix.dto.ResponsePixAlteracaoDTO;
import itau.com.br.cadastrochavespix.model.ChavePix;
import itau.com.br.cadastrochavespix.service.ChavePixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/chavespix")
public class ChavePixController {

    final private Logger logger = Logger.getLogger(ChavePixController.class.getName());
    @Autowired
    private final ChavePixService chavePixService;

    @Autowired
    public ChavePixController(ChavePixService chavePixService) {
        this.chavePixService = chavePixService;
    }

    @PostMapping(value = "/incluir")
    @Operation(summary = "Create a Pix Key", description = "Create a Pix Key",
            tags = {"Gerenciador Chaves Pix"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            //todo tirar o array schema é so um schema
                                            schema = @Schema(implementation = ChavePixDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<UUID> incluirChavePix(@Validated @RequestBody ChavePixDTO chavePixDTO) {
        Optional<ChavePix> chavePix = chavePixService.incluirChavePix(chavePixDTO);
        return chavePix.map(value -> ResponseEntity.ok().body(value.getId()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build());
    }


    @Operation(summary = "Key Change", description = "Alteração de Chave Pix , atravez do ID a chave pix pode ter alguns parametros modificados" +
            "Os parametros Chave ID , Tipo Chave, e Valor Chave não podem ser alterados" +
            "Combinações Bloqueadas : DataInclusaoChavePix && DataInativacaoChavePix" +
            "Quando ID é informado nenhum outro parametro devera ser informado ",
            tags = {"Gerenciador Chaves Pix"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ChavePixConsultaDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/alterar/")
    public ResponseEntity<ResponsePixAlteracaoDTO> alterarChavePix(@Validated @RequestBody ChavePixAlteracaoDTO chavePixDTO) {
        var chavePix = chavePixService.alterarChavePix(chavePixDTO);
        return ResponseEntity.ok(chavePix);
    }

    @Operation(summary = "Parameterized Search", description = "Busca de chave pix parametrizada" +
            "Combinações Bloqueadas : DataInclusaoChavePix && DataInativacaoChavePix" +
            "Quando ID é informado nenhum outro parametro devera ser informado ",
            tags = {"Gerenciador Chaves Pix"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ChavePixConsultaDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping("/consultar")
    public ResponseEntity<List<ChavePixConsultaDTO>> consultaCombinada(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String tipoChave,
            @RequestParam(required = false) String agencia,
            @RequestParam(required = false) String conta,
            @RequestParam(required = false) String nomeCorrentista,
            @RequestParam(required = false) String sobreNomeCorrentista,
            @RequestParam(required = false) String dataHoraInclusaoChave,
            @RequestParam(required = false) String dataHoraInativacaoChave) {

        var pixList = chavePixService.consultaCombinada(id,tipoChave, agencia, conta,
                nomeCorrentista,sobreNomeCorrentista, dataHoraInclusaoChave, dataHoraInativacaoChave);

        if (pixList.get().isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return pixList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build());
         }

    @Operation(summary = "Key inactivation", description = "Inativaçao de Chave Pix , " +
            "Apos ser realizada não podera ser desfeita",
            tags = {"Gerenciador Chaves Pix"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",

                                            schema = @Schema(implementation = ChavePixDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping("/inativar/{id}")
    public ResponseEntity<ChavePix> inativarChavePix(@PathVariable UUID id) {
        Optional<ChavePix> optionalChavePix = chavePixService.deletarChavePix(id);
        return ResponseEntity.ok(optionalChavePix.get());
    }
}