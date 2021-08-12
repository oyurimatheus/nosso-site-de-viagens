package me.oyurimatheus.nossositedeviagens.compartilhado.validacoes;

import com.sun.istack.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Field;
import java.util.function.Function;

import static java.lang.String.format;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 *
 * @param <T> classe que sera validada
 * @param <P> tipo do parametro a ser validado, caso seja nulo, validador retonara valido
 */
public class CampoUnicoValidator<T, P> implements Validator {

    private final String campo;
    private final String codigoDeErro;
    private final Class<? extends T> classeParaValidar;
    private final Function<P, Boolean> funcaoDeExistencia;

    /**
     *
     * @param campo o campo que sera validado
     * @param codigoDeErro o codigo de erro que sera retornado a aplicacao que fez a requisicao
     * @param classeParaValidar o tipo da classe que sera validada
     * @param funcaoDeExistencia uma funcao que recebe o argumento #P
     *
     * @throws IllegalArgumentException se algum campo estiver invalido
     */
    public CampoUnicoValidator(@NotEmpty String campo,
                                @NotEmpty String codigoDeErro,
                                @NotNull Class<? extends T> classeParaValidar,
                                @NotNull Function<P, Boolean> funcaoDeExistencia) {

        hasText(campo, "campo precisa ter um texto valido");
        hasText(codigoDeErro, "codigoDeErro precisa ter um texto valido");
        notNull(classeParaValidar, "classeParaValidar nao pode ser nula");
        notNull(funcaoDeExistencia, "funcaoDeExistencia nao pode ser nula");

        this.campo = campo;
        this.codigoDeErro = codigoDeErro;
        this.classeParaValidar = classeParaValidar;
        this.funcaoDeExistencia = funcaoDeExistencia;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return classeParaValidar.isAssignableFrom(clazz);
    }

    /**
     *
     * @param paraValidar o objeto a ser validado
     * @param erros os erros armazenados
     *
     * @throws IllegalArgumentException se o campo nao existir ou estiver inacessivel
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object paraValidar, Errors erros) {

        try {
            Field campoParaValidar = classeParaValidar.getDeclaredField(campo);
            campoParaValidar.setAccessible(true);
            Object valorDoCampo = campoParaValidar.get(paraValidar);

            if (valorDoCampo == null) {
                return;
            }

            Boolean achou = funcaoDeExistencia.apply((P) valorDoCampo);
            if (achou) {
                erros.rejectValue(campo, codigoDeErro, format("%s ja esta registrado", campo));
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}