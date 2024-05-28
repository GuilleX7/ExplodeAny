package io.github.guillex7.explodeany.compat.common.api;

import java.util.function.Consumer;

import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.compat.common.event.IBukkitListener;

public interface IBukkitListenerUtils {
    IBukkitListener createBlockExplodeListener(Consumer<EanyBlockExplodeEvent> eanyBlockExplodeEventConsumer);
}
