package core.game.node.entity.combat.special;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.handlers.MeleeSwingHandler;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.player.Player;
import core.game.node.entity.state.EntityState;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphics;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Handles the Staff of the Dead's special attack.
 * @author Splinter
 * @version 1.0
 */
@Initializable
public final class StaffOfTheDeadSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

	/**
	 * The special energy required.
	 */
	private static final int SPECIAL_ENERGY = 100;

	/**
	 * The attack animation.
	 */
	private static final Animation ANIMATION = new Animation(10518, 10, Priority.HIGH);

	/**
	 * The graphic.
	 */
	private static final Graphics GRAPHIC = new Graphics(1589);

	@Override
	public Object fireEvent(String identifier, Object... args) {
		switch (identifier) {
		case "instant_spec":
		case "ncspec":
			return true;
		}
		return null;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		CombatStyle.MELEE.getSwingHandler().register(14726, this);
		return this;
	}

	@Override
	public int swing(Entity entity, Entity victim, BattleState state) {
		Player p = (Player) entity;
		if(p.getStateManager().hasState(EntityState.STAFF_OF_THE_DEAD)){
			p.sendMessage("You are already affected by the special move of the staff.");
			p.getSettings().toggleSpecialBar();
			return -1;
		}
		if (!p.getSettings().drainSpecial(SPECIAL_ENERGY))
			return -1;
		p.visualize(ANIMATION, GRAPHIC);
		p.getStateManager().set(EntityState.STAFF_OF_THE_DEAD, 100);
		return -1;
	}

}
