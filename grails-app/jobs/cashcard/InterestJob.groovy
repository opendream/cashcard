package cashcard


class InterestJob {
    static triggers = {
        // schedule for everyday at midnight
        cron name: 'interestCronTrigger', cronExpression: "0 0 0 * * ?"
    }

    def scheduleService
    def group = "interest"

    def execute() { 
        scheduleService.updateInterest()
    }
}
