package th.co.opendream.cashcard

import org.springframework.transaction.support.TransactionSynchronizationManager
import grails.util.Environment


class SchemaService {
    def sessionFactory

    def create(schema) {
        println "schema > ${schema}"
        def queryCreateTables = ""

        if (Environment.current == Environment.DEVELOPMENT) {
            queryCreateTables += """
                DROP SCHEMA IF EXISTS ${schema} CASCADE;
            """
        }

        queryCreateTables += """
            CREATE SCHEMA ${schema};

            CREATE TABLE ${schema}.interest_rate
            (
              id bigint NOT NULL,
              "version" bigint NOT NULL,
              date_created timestamp without time zone NOT NULL,
              last_updated timestamp without time zone NOT NULL,
              rate numeric(19,2) NOT NULL,
              start_date timestamp without time zone NOT NULL,
              CONSTRAINT interest_rate_pkey PRIMARY KEY (id),
              CONSTRAINT interest_rate_start_date_key UNIQUE (start_date)
            );

            CREATE TABLE ${schema}.policy
            (
              id bigint NOT NULL,
              "version" bigint NOT NULL,
              "key" character varying(255) NOT NULL,
              "value" character varying(255) NOT NULL,
              CONSTRAINT policy_pkey PRIMARY KEY (id),
              CONSTRAINT policy_key_key UNIQUE (key)
            );

            CREATE TABLE ${schema}."transaction"
            (
              id bigint NOT NULL,
              "version" bigint NOT NULL,
              amount numeric(19,2) NOT NULL,
              code character varying(255) NOT NULL,
              date timestamp without time zone NOT NULL,
              member_id bigint NOT NULL,
              tx_type character varying(255) NOT NULL,
              "class" character varying(255) NOT NULL,
              activity character varying(255),
              net numeric(19,2),
              remainder numeric(19,2),
              fee numeric(19,2),
              interest numeric(19,2),
              CONSTRAINT transaction_pkey PRIMARY KEY (id)
            );

        """
        println queryCreateTables

        def holder = TransactionSynchronizationManager.getResource(sessionFactory)
        def session = holder.getSession();
        def conn = session.connection();
        groovy.sql.Sql sql = new groovy.sql.Sql(conn)

        sql.execute(queryCreateTables)
    }

    def with(String schema, Closure closure) {
        def holder = TransactionSynchronizationManager.getResource(sessionFactory)
        def session = holder.getSession();
        def conn = session.connection();
        groovy.sql.Sql sql = new groovy.sql.Sql(conn)

        def ret
        try {
            def cmd = String.format("set search_path to %s, public", schema)
            sql.execute(cmd)
            ret = closure()
            session.flush()
        } catch (e) {
            e.printStackTrace()
        }

        ret
    }
}