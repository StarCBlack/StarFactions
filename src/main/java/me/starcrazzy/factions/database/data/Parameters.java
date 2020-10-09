package me.starcrazzy.factions.database.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author oNospher
 **/

@AllArgsConstructor
@Getter
public class Parameters<K, V> {

    private final K key;
    private final V value;

}