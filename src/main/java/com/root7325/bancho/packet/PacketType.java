package com.root7325.bancho.packet;

/**
 * @author kate on 02.05.2025
 */
public enum PacketType {
    Osu_SendUserStatus,

    Osu_SendIrcMessage,

    Osu_Exit,

    Osu_RequestStatusUpdate,

    Osu_Pong,

    Bancho_LoginReply,

    Bancho_CommandError,

    Bancho_SendIrcMessage,

    Bancho_Ping,

    Bancho_HandleIrcChangeUsername,

    Bancho_HandleIrcQuit,

    Bancho_HandleIrcJoin,

    Bancho_HandleOsuUpdate,

    Bancho_HandleOsuQuit,

    Bancho_SpectatorJoined,

    Bancho_SpectatorLeft,

    Bancho_SpectateFrames,

    Osu_StartSpectating,

    Osu_StopSpectating,

    Osu_SpectateFrames,

    Bancho_VersionUpdate,

    Osu_ErrorReport,

    Osu_CantSpectate,

    Bancho_SpectatorCantSpectate,

    Bancho_GetAttention,

    Bancho_Announce,

    Osu_SendIrcMessagePrivate,

    Bancho_MatchUpdate,

    Bancho_MatchNew,

    Bancho_MatchDisband,

    Osu_LobbyPart,

    Osu_LobbyJoin,

    Osu_MatchCreate,

    Osu_MatchJoin,

    Osu_MatchPart,

    Bancho_LobbyJoin,

    Bancho_LobbyPart,

    Bancho_MatchJoinSuccess,

    Bancho_MatchJoinFail,

    Osu_MatchChangeSlot,

    Osu_MatchReady,

    Osu_MatchLock,

    Osu_MatchChangeSettings,

    Bancho_FellowSpectatorJoined,

    Bancho_FellowSpectatorLeft,

    Osu_MatchStart,

    AllPlayersLoaded,

    Bancho_MatchStart,

    Osu_MatchScoreUpdate,

    Bancho_MatchScoreUpdate,

    Osu_MatchComplete,

    Bancho_MatchTransferHost,

    Osu_MatchChangeMods,

    Osu_MatchLoadComplete,

    Bancho_MatchAllPlayersLoaded,

    Osu_MatchNoBeatmap,

    Osu_MatchNotReady,

    Osu_MatchFailed,

    Bancho_MatchPlayerFailed,

    Bancho_MatchComplete,

    Osu_MatchHasBeatmap,

    Osu_MatchSkipRequest,

    Bancho_MatchSkip,

    Bancho_Unauthorised,

    Osu_ChannelJoin,

    Bancho_ChannelJoinSuccess,

    Bancho_ChannelAvailable,

    Bancho_ChannelRevoked,

    Bancho_ChannelAvailableAutojoin,

    Osu_BeatmapInfoRequest,

    Bancho_BeatmapInfoReply,

    Osu_MatchTransferHost,

    Bancho_LoginPermissions,

    Bancho_FriendsList,

    Osu_FriendAdd,

    Osu_FriendRemove,

    Bancho_ProtocolNegotiation
}