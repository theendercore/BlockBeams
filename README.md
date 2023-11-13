# Block Beams

A very simple mod that spawns a beam of colored particles above a block
---

A very simple mod that spawns a beam of colored particles above a block if the block has 3 block of air or other 
"passable" blocks above it *(see config for more info)*. Which helps you spot the block better. I
originally came up with this idea when I was mining for ores with my world eater. But with how big the 1.18 caves are
this is going to be useful there as well. Plus it can also be used as an aesthetic feature, in case you want to have a
water fountain you can make a not useful block have blue particles and boom you have a fountain.

#### Warning

Some server owners / admins may see this mod as unfair. So be respectful and ask the server owners / admins if you can
you this mod. Also mention the "server side" config of the mod. *(Se bellow)*

### Dependencies

This mod Depends on: [Kotlin](https://modrinth.com/mod/fabric-language-kotlin) *(My apologies for dependency)*

### Config

This mod has extensive configuration for what little it adds.
There are 2 types of config :

<details>
    <summary>"Server" side</summary>
Block Beams is not supported on the server but there is a server side type config.


The only think you can configure server side is the block pass list. It is configured by a vanilla tag, so you just
have to set up a tag on the severer, then all the clients that use the mod will be configured, no need to install the
mod on the server. *(Only thing to add is that the clients can override if they use the server tag or not)*

If you wish to:

- make your own tag just
  go [here for the example pack](https://github.com/theendercore/BlockBeams/tree/1.19/examples/exmple_pack) and then
  just reconfigure it as you like.
- use the [default pack](https://github.com/theendercore/BlockBeams/raw/1.19/examples/DefaultPack.zip)
- disable to mod [empty pack](https://github.com/theendercore/BlockBeams/raw/1.19/examples/EmptyPack.zip)

</details>

<details>
    <summary>Client side</summary>

As stated in server config most configs are on the client:

- "blockBeams" is the list of blocks that will emit the particles and there color. It has a very simple format
  of `["namespace:block": hex color]`
- "blockCheckType" is the way the mod check for what blocks to pass through
    - SERVER_ONLY - Gets the tag from the server and checks based on that, if the server has no tag then the mod is
      disabled \[DEFAULT\]
    - CLIENT_ONLY - Uses the client tag from his config (Use only if it is okay to do on the server)
    - ~~SERVER_THEN_CLIENT - Not implemented~~
- "clientTag" The client equivalent of the server side tag. Works like vanilla tag.

</details>

There are plans for a config screen, but that is for later in the future when our ui lib is out.

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/M4M7DWJCH)



