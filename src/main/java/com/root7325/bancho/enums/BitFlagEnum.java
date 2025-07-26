package com.root7325.bancho.enums;

import java.util.EnumSet;

/**
 * Interface for enums representing bit flag values.
 * Provides functionality to work with flag combinations as bitmasks.
 *
 * @author root7325 on 26.07.2025
 */
public interface BitFlagEnum {
    /**
     * Gets bitmask value for this enum constant.
     *
     * @return bitmask value
     */
    int getBitMask();

    /**
     * Checks whether this flag is set in the given bitmask.
     *
     * @param flags bitmask to check
     * @return {@code true} if this flag is set in the bitmask, {@code false} otherwise
     */
    default boolean isSet(int flags) {
        return (flags & getBitMask()) != 0;
    }

    /**
     * Decodes a bitmask into a set of enum constants.
     *
     * @param b         bitmask to decode
     * @param enumClass class of the enum
     * @param <E>       type of enum implementing BitFlagEnum
     * @return EnumSet containing all flags that are set in the bitmask
     */
    static <E extends Enum<E> & BitFlagEnum> EnumSet<E> decodeFlags(int b, Class<E> enumClass) {
        EnumSet<E> states = EnumSet.noneOf(enumClass);
        for (E state : enumClass.getEnumConstants()) {
            if (state.isSet(b)) {
                states.add(state);
            }
        }
        return states;
    }

    /**
     * Encodes a set of enum constants into a bitmask.
     *
     * @param states set of enum constants to encode
     * @param <E>    type of enum implementing BitFlagEnum
     * @return bitmask representing all flags in the set
     */
    static <E extends Enum<E> & BitFlagEnum> int encodeFlags(EnumSet<E> states) {
        int result = 0;
        for (E state : states) {
            result |= state.getBitMask();
        }
        return result;
    }
}