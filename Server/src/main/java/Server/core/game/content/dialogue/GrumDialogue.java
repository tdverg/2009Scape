package core.game.content.dialogue;

import core.game.node.entity.npc.NPC;
import core.plugin.Initializable;
import core.game.node.entity.player.Player;

/**
 * Represents the grum dialogue plugin.
 * @author 'Vexia
 * @version 1.0
 */
@Initializable
public final class GrumDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code GrumDialogue} {@code Object}.
	 */
	public GrumDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code GrumDialogue} {@code Object}.
	 * @param player the player.
	 */
	public GrumDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new GrumDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.HALF_GUILTY, "Would you like to buy or sell some gold jewellery?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendOptions("Select an Option", "Yes, please.", "No, I'm not that rich.");
			stage = 1;
			break;
		case 1:
			switch (buttonId) {
			case 1:
				end();
				npc.openShop(player);
				break;
			case 2:
				interpreter.sendDialogues(player, FacialExpression.HALF_GUILTY, "No, I', not that rich.");
				stage = 20;
				break;

			}
			break;
		case 20:
			interpreter.sendDialogues(npc, FacialExpression.HALF_GUILTY, "Get out, then! We don't want any riff-raff in here.");
			stage = 21;
			break;
		case 21:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 556 };
	}
}
