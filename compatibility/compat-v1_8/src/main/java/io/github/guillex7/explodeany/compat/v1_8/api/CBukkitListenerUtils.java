package io.github.guillex7.explodeany.compat.v1_8.api;

import java.util.function.Consumer;

import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.compat.common.event.IBukkitListener;

public class CBukkitListenerUtils implements IBukkitListenerUtils {
    @Override
    public IBukkitListener createBlockExplodeListener(Consumer<EanyBlockExplodeEvent> eanyBlockExplodeEventConsumer) {
        return new CBlockExplodeListener();
    }
}
