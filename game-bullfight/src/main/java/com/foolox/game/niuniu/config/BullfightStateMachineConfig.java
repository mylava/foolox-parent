package com.foolox.game.niuniu.config;

import com.foolox.base.constant.game.PlayerAction;
import com.foolox.base.constant.game.RoomStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 02/08/2019
 */
@Configuration
@EnableStateMachine(name = {"bullfight"})
public class BullfightStateMachineConfig extends EnumStateMachineConfigurerAdapter<RoomStatus, PlayerAction> {
    @Override
    public void configure(StateMachineStateConfigurer<RoomStatus, PlayerAction> states) throws Exception {
        states.withStates()
                .initial(RoomStatus.NONE)
                .states(EnumSet.allOf(RoomStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RoomStatus, PlayerAction> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(RoomStatus.NONE).target(RoomStatus.CREATE)
                    .event(PlayerAction.CREATE).action(null)
                    .and()
                .withExternal()
                    .source(RoomStatus.CREATE).target(RoomStatus.WAITTING)
                    .event(PlayerAction.JOIN).action(null)
                    .and()
                .withExternal()
                    .source(RoomStatus.WAITTING).target(RoomStatus.READY)
                    .event(PlayerAction.ENOUGH).action(null)
                    .and()
                .withExternal()
                    .source(RoomStatus.READY).target(RoomStatus.BEGIN)
                    .event(PlayerAction.AUTO).action(null)
                    .and()
                .withExternal()
                    .source(RoomStatus.BEGIN).target(RoomStatus.LASTHANDS)
                    .event(PlayerAction.RAISEHANDS).action(null)
                    .and()
                .withExternal()
                    .source(RoomStatus.LASTHANDS).target(RoomStatus.PLAY)
                    .event(PlayerAction.PLAYCARDS).action(null)
                    .and()
                .withExternal()
                    .source(RoomStatus.PLAY).target(RoomStatus.END)
                    .event(PlayerAction.ALLCARDS).action(null);
    }
}
