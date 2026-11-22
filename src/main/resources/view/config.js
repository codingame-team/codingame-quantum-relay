import { GraphicEntityModule } from './entity-module/GraphicEntityModule.js';
import { TooltipModule } from './tooltip-module/TooltipModule.js';
import { EndScreenModule } from './endscreen-module/EndScreenModule.js';
import { ToggleModule } from './toggle-module/ToggleModule.js';

export const modules = [
    GraphicEntityModule,
    TooltipModule,
    EndScreenModule,
    ToggleModule
];

export const playerColors = [
    '#ff1d5c', // Player 0: Crimson Corp
    '#22a1e4'  // Player 1: Cyan Syndicate
];

export const gameName = 'QuantumRelay';

export const options = [
    ToggleModule.defineToggle({
        toggle: 'debugRanges',
        title: 'SHOW RANGES & IDS',
        values: {
            'ON': true,
            'OFF': false
        },
        default: true
    })
];

export const overlayAlpha = 0;
