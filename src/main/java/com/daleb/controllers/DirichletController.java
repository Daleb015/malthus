package com.daleb.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.jacop.core.Store;
import org.jacop.floats.constraints.LinearFloat;
import org.jacop.floats.core.FloatDomain;
import org.jacop.floats.core.FloatVar;
import org.jacop.floats.search.SplitSelectFloat;
import org.jacop.search.DepthFirstSearch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daleb.dto.DirichletRequestDTO;
import com.daleb.dto.DirichletResponseDTO;
import com.daleb.dto.Solucion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("dirichlet/api/")
@Api(tags = "Malthus")
public class DirichletController {

  @PostMapping("calculate")
  @ApiOperation(value = "Calcular funcion", notes = "Servicio para calcula la funcion que es la solucion en ecuaciones derivadas parciales en el "
      + "interior de un dominion de R por medio de el metodo de leibman laplace de cinco puntos:  Dada una función {\\displaystyle f}f con valores "
      + "en todos los puntos del contorno de una región en {\\displaystyle \\mathbb {R} ^{n}}\\mathbb {R} ^{n}. ¿Existe una única función continua "
      + "{\\displaystyle u}u dos veces continuamente diferenciable en el interior y continua en el contorno, tal que u es armónica en el interior y "
      + "{\\displaystyle u=f}{\\displaystyle u=f} en el contorno?")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Calculo correcto"),
      @ApiResponse(code = 400, message = "Campos en la solicitud errados") })
  public ResponseEntity<DirichletResponseDTO> calcular(
      @Valid @RequestBody DirichletRequestDTO dirichletRequestDTO) {
    try {

      int r = dirichletRequestDTO.getVectorR();
      int c = dirichletRequestDTO.getVectorC();
      double precision = dirichletRequestDTO.getPrecision();
      double Z = dirichletRequestDTO.getLimiteInferior();
      double M = dirichletRequestDTO.getLimiteSuperior();

      Store store = new Store();

      FloatDomain.setPrecision(precision);

      FloatVar[][] x = new FloatVar[r + 1][c + 1];
      for (int i = 0; i < r + 1; i++)
        for (int j = 0; j < c + 1; j++)
          if (i == 0)
            x[i][j] = new FloatVar(store, "r[" + i + "][" + j + "]", Z, Z);
          else if (i == r || j == 0 || j == c)
            x[i][j] = new FloatVar(store, "r[" + i + "][" + j + "]", M, M);
          else
            x[i][j] = new FloatVar(store, "r[" + i + "][" + j + "]", Z, M);
      for (int i = 1; i < r; i++)
        for (int j = 1; j < c; j++)
          store.impose(new LinearFloat(store,
              new FloatVar[] { x[i][j], x[i - 1][j], x[i][j - 1], x[i + 1][j], x[i][j + 1] },
              new double[] { -4.0, 1.0, 1.0, 1.0, 1.0 }, "==", 0.0));
      FloatVar[] xs = new FloatVar[(r + 1) * (c + 1)];
      int n = 0;
      for (int i = 0; i < r + 1; i++)
        for (int j = 0; j < c + 1; j++)
          xs[n++] = x[i][j];
      DepthFirstSearch<FloatVar> label = new DepthFirstSearch<FloatVar>();
      SplitSelectFloat<FloatVar> s = new SplitSelectFloat<FloatVar>(store, xs, null);
      label.setAssignSolution(true);
      s.leftFirst = false;
      int errores = label.getWrongDecisions();
      int nodos = label.getNodes();
      int decisiones = label.getDecisions();
      int profundidad = label.getMaximumDepth();
      int trampas = label.getBacktracks();
      label.labeling(store, s);
      log.info("aqui no ha impreso 5");
      List<Solucion> results = new ArrayList<>();

      for (int i = 0; i < r + 1; i++) {
        for (int j = 0; j < c + 1; j++) {
          Solucion solucion = new Solucion();
          solucion.setI(i);
          solucion.setJ(j);
          solucion.setValor(x[i][j].value());
          results.add(solucion);
          System.out.printf("%.2f\t", x[i][j].value());
          System.out.println();
        }

      }
      System.out.println();
      System.out.println("Precision = " + FloatDomain.precision());
      DirichletResponseDTO dirichletResponseDTO = new DirichletResponseDTO();
      dirichletResponseDTO.setDecisiones(decisiones);
      dirichletResponseDTO.setMaxProfundid(profundidad);
      dirichletResponseDTO.setNodosErrados(errores);
      dirichletResponseDTO.setTrampas(trampas);
      dirichletResponseDTO.setPrecision(FloatDomain.precision());
      dirichletResponseDTO.setNodos(nodos);
      dirichletResponseDTO.setSoluciones(results);
      return new ResponseEntity<DirichletResponseDTO>(dirichletResponseDTO, HttpStatus.OK);
    } catch (Exception e) {
      DirichletResponseDTO dirichletResponseDTO = new DirichletResponseDTO();
      dirichletResponseDTO.setMsgError("Error datos enviados para computo");
      return new ResponseEntity<>(dirichletResponseDTO, HttpStatus.BAD_REQUEST);
    }
  }

}
