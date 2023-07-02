package ru.otuskotlin.public.bookingservice.common.repo

interface IMeetingRepository {

    suspend fun createMeeting(request :DbMeetingRequest) :DbMeetingResponse

    suspend fun readMeeting(request :DbMeetingIdRequest) :DbMeetingResponse

    suspend fun updateMeeting(request :DbMeetingRequest) :DbMeetingResponse

    suspend fun deleteMeeting(request :DbMeetingIdRequest) :DbMeetingResponse

    suspend fun searchMeeting(request :DbEmployeeIdRequest) :DbMeetingsResponse

    suspend fun searchSlots(request :DbEmployeeIdRequest) :DbSlotsResponse

    companion object{
        val NONE = object :IMeetingRepository{
            override suspend fun createMeeting(request: DbMeetingRequest): DbMeetingResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readMeeting(request: DbMeetingIdRequest): DbMeetingResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateMeeting(request: DbMeetingRequest): DbMeetingResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteMeeting(request: DbMeetingIdRequest): DbMeetingResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchMeeting(request: DbEmployeeIdRequest): DbMeetingsResponse {
                TODO("Not yet implemented")
            }
            override suspend fun searchSlots(request: DbEmployeeIdRequest): DbSlotsResponse {
                TODO("Not yet implemented")
            }

        }
    }





}