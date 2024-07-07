### Healthbar duration
This determines how long healthbars stay on screen during combat.
Healthbar durations are set under "duration". This duration is in ticks (20 per second).
With a duration set to 60, healthbars disappear after 3 seconds of not atacking entities.

### Custom healthbars per entity type
Custom styles are applied under "styles"

Here's an exaggerated example:
                                                    
styles:                                       
  Zombie:                                       
    color: PURPLE                               
  Skeleton:                                 
    color: WHITE                            
    style: SEGMENTED_10                            
  Player:                            
    color: BLUE                            
  Animals:                            
    color: GREEN                            
  Sheep:                            
    color: WHITE                            
  Monster:                            
    style: SEGMENTED_6                            
    
Styles are determined from top to bottom, when the attacked entity matches it will pick that style. Because of this, "Sheep" will never get matched in the example above, since it's already matched as part of "Animals".
Keep this in mind as you add your custom styles. Keep broader matches in the bottom.
The type of monster can be any subclass of "LivingEntity", but here's a full list of options (as of 1.12.1):

AbstractHorse, Ageable, Ambient, Animals, ArmorStand, Bat, Blaze, CaveSpider, ChestedHorse, Chicken, ComplexLivingEntity, Cow, Creature, Creeper, Donkey, ElderGuardian, EnderDragon, Enderman, Endermite, Evoker, Flying, Ghast, Giant, Golem, Guardian, Horse, HumanEntity, Husk, Illager, Illusioner, IronGolem, Llama, MagmaCube, Monster, Mule, MushroomCow, NPC, Ocelot, Parrot, Pig, PigZombie, Player, PolarBear, Rabbit, Sheep, Shulker, Silverfish, Skeleton, SkeletonHorse, Slime, Snowman, Spellcaster, Spider, Squid, Stray, Vex, Villager, Vindicator, WaterMob, Witch, Wither, WitherSkeleton, Wolf, Zombie, ZombieHorse, ZombieVillager


Possible bar styles:              
SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20

Possible bar colors:                      
PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE
