import psycopg2
from schedule import every, repeat, run_pending
import time


def main():
    conn = None
    cur = None
    try:
        conn = psycopg2.connect(
            host = 'localhost',
            dbname = 'myhomedb',
            user = 'postgres',
            password = 'kvadratnidjavo3',
            port = 5432
        )

        cur = conn.cursor()
   
        def job():
            print("Velja je kriv jebes ga")
            insert_script = 'insert into device (name, time_stamp, message) values (%s, %s, %s)'
            insert_value = ('pegla', 1653004800, 'upalila se')
            cur.execute(insert_script, insert_value)
            conn.commit()  
        
        every(5).seconds.do(job)

        while True:
            run_pending()
            time.sleep(1)


        print("END")
  
    except Exception as error:
        print(error)
    finally:
        if cur is not None:
            cur.close()
        if conn is not None:
            conn.close()

if __name__ == "__main__":
    main()     

# create_script = ''' create table if not exists device 
        #                 (
        #                     id bigint not null default nextval(\'device_id_seq\'::regclass),
        #                     name varchar(20) not null,
        #                     time_stamp bigint not null,
        #                     message varchar (40) not null
        #                 )
        #                 tablespace pg_default;

        #                 alter table public.device
        #                 owner to postgres;
        #                 '''
        # cur.execute(create_script)