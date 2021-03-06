package me.realized.duels.data;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.kit.Kit;
import me.realized.duels.queue.Queue;
import me.realized.duels.queue.sign.QueueSign;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class QueueSignData {

    private final LocationData location;
    private final String kit;
    private final int bet;

    public QueueSignData(final QueueSign sign) {
        this.location = new LocationData(sign.getLocation());

        final Queue queue = sign.getQueue();
        this.kit = queue.getKit() != null ? queue.getKit().getName() : null;
        this.bet = queue.getBet();
    }

    public QueueSign toQueueSign(final DuelsPlugin plugin) {
        final Location location = this.location.toLocation();

        if (location.getWorld() == null) {
            return null;
        }

        final Block block = location.getBlock();

        if (!(block.getState() instanceof Sign)) {
            return null;
        }

        final Kit kit = this.kit != null ? plugin.getKitManager().get(this.kit) : null;
        Queue queue = plugin.getQueueManager().get(kit, bet);

        if (queue == null) {
            plugin.getQueueManager().create(kit, bet);
            queue = plugin.getQueueManager().get(kit, bet);
        }

        return new QueueSign(location,
            plugin.getLang().getMessage("SIGN.format", "kit", this.kit != null ? this.kit : plugin.getLang().getMessage("none"), "bet_amount", bet), queue);
    }
}