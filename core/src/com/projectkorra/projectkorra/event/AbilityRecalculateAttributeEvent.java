package com.projectkorra.projectkorra.event;

import com.google.common.primitives.Primitives;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.attribute.AttributeModification;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.type.PrimitiveType;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;


public class AbilityRecalculateAttributeEvent extends Event {
        private static final HandlerList handlers = new HandlerList();

    CoreAbility ability;
    String attribute;
    Object originalValue;

    Set<AttributeModification> modifications = new TreeSet<>(Comparator.comparingInt(AttributeModification::getPriority));

    public AbilityRecalculateAttributeEvent(final CoreAbility ability, final String attribute, final Object originalValue) {
        this.ability = ability;
        this.attribute = attribute;
        this.originalValue = originalValue;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Object getOriginalValue() {
        return originalValue;
    }

    public <T extends Annotation> T getMarker(Class<T> markerClass) {
        return (T) CoreAbility.getAttributeCache(ability).get(markerClass);
    }

    public boolean hasMarker(Class<? extends Annotation> markerClass) {
        return CoreAbility.getAttributeCache(ability).containsKey(markerClass);
    }

    public void addModification(final AttributeModification modification) {
        modifications.add(modification);
    }

    public int getOriginalValueAsInt() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }

        return ((Number)originalValue).intValue();
    }

    public float getOriginalValueAsFloat() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }
        return ((Number)originalValue).floatValue();
    }

    public double getOriginalValueAsDouble() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }
        return ((Number)originalValue).doubleValue();
    }

    public long getOriginalValueAsLong() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue ? 1 : 0;
        }
        return ((Number)originalValue).longValue();
    }

    public boolean getOriginalValueAsBoolean() {
        if (originalValue instanceof Boolean) {
            return (boolean) originalValue;
        }
        return ((Number)originalValue).intValue() % 2 == 1;
    }

    @NotNull
    public CoreAbility getAbility() {
        return ability;
    }

    public String getAttribute() {
        return attribute;
    }

    public Set<AttributeModification> getModifications() {
        return modifications;
    }
}
