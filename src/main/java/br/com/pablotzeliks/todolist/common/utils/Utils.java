package br.com.pablotzeliks.todolist.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe utilitária com métodos auxiliares para manipulação de objetos.
 * <p>
 * Esta classe fornece métodos estáticos para operações comuns de cópia
 * e manipulação de propriedades de beans Java, especialmente úteis
 * para operações de atualização parcial de entidades.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 */
public class Utils {

    /**
     * Copia as propriedades não nulas de um objeto de origem para um objeto de destino.
     * <p>
     * Este método é especialmente útil para operações de PATCH/UPDATE onde apenas
     * os campos enviados pelo cliente (não nulos) devem ser atualizados, preservando
     * os valores existentes dos campos não informados.
     * </p>
     *
     * @param src objeto de origem contendo as propriedades a serem copiadas
     * @param target objeto de destino que receberá as propriedades não nulas
     */
    public static void copyNonNullProperties(Object src, Object target) {

        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * Obtém os nomes das propriedades que possuem valor nulo em um objeto.
     * <p>
     * Este método utiliza introspecção de beans para identificar todas as propriedades
     * do objeto que estão com valor {@code null}. É utilizado internamente por
     * {@link #copyNonNullProperties(Object, Object)} para excluir propriedades nulas
     * durante a cópia.
     * </p>
     *
     * @param source objeto a ser inspecionado
     * @return array contendo os nomes das propriedades com valor nulo
     */
    public static String[] getNullPropertyNames(Object source) {

        Set<String> emptyNames = new HashSet<>();
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        for (PropertyDescriptor pd : pds) {

            Object srcValue = src.getPropertyValue(pd.getName());

            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];

        return emptyNames.toArray(result);
    }
}