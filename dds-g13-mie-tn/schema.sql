
    create table Actuator (
        actuator_id bigint not null auto_increment,
        device_id bigint,
        primary key (actuator_id)
    );

    create table Administrator (
        administrator_id bigint not null auto_increment,
        password varchar(255),
        username varchar(255),
        fullname varchar(255),
        registrationDate datetime,
        residenceAddress varchar(255),
        primary key (administrator_id)
    );

    create table Area (
        area_id bigint not null auto_increment,
        area_name varchar(255),
        x double not null,
        y double not null,
        radius float,
        primary key (area_id)
    );

    create table Category (
        category_id bigint not null auto_increment,
        fixedCharge decimal(19,2),
        maxConsumption float,
        minConsumption float,
        name varchar(255),
        variableCharge decimal(19,2),
        primary key (category_id)
    );

    create table Client (
        client_id bigint not null auto_increment,
        password varchar(255),
        username varchar(255),
        fullname varchar(255),
        registrationDate datetime,
        residenceAddress varchar(255),
        number_id varchar(255),
        type_id varchar(255),
        phone_number varchar(255),
        score integer,
        category_id bigint,
        primary key (client_id)
    );

    create table Device (
        device_type varchar(31) not null,
        device_id bigint not null auto_increment,
        name varchar(255),
        dailyUseEstimation decimal(19,2),
        residence_id int,
        deviceinfo_id bigint,
        primary key (device_id)
    );
    
    create table DeviceAction (
        deviceAction_type varchar(31) not null,
        deviceAction_id bigint not null auto_increment,
        primary key (deviceAction_id)
    );

    create table DeviceInfo (
        deviceInfo_id bigint not null auto_increment,
        bajoConsumo bit,
        consumption double precision,
        description varchar(255),
        inteligente bit,
        maxHsUse integer,
        minHsUse integer,
        name varchar(255),
        primary key (deviceInfo_id)
    );

    create table Measure (
        measure_id bigint not null auto_increment,
        magnitude varchar(255),
        value decimal(19,2),
        sensor_id bigint,
        primary key (measure_id)
    );

    create table Residence (
        residence_id bigint not null auto_increment,
        address varchar(255),
        area_id bigint,
        client_id bigint,
        x double not null,
        y double not null,
        primary key (residence_id)
    );

    create table Rule (
        rule_id bigint not null auto_increment,
        actuator_id bigint,
        rule_type varchar(30) not null,
        primary key (rule_id)
    );

    create table Sensor (
        sensor_id bigint not null auto_increment,
        intervalInSeconds float,
        device_id bigint,
        sensor_type varchar(30) not null,
        primary key (sensor_id)
    );

    create table StateHistory (
        stateHistory_id bigint not null auto_increment,
        end datetime,
        start datetime,
        state varchar(255),
        device_id bigint,
        primary key (stateHistory_id)
    );

    create table TimeIntervalDevice (
        timeIntervalDevice_id bigint not null auto_increment,
        consuming bit,
        end datetime,
        start datetime,
        device_id bigint,
        primary key (timeIntervalDevice_id)
    );

    create table Transformer (
        transformer_id bigint not null auto_increment,
        x double not null,
        y double not null,
        area_id bigint,
        primary key (transformer_id)
    );
    
    create table ActuatorSensor (
    	actuator_id bigint,
    	sensor_id bigint
    );

    alter table Actuator 
        add constraint FK_kohhg7urkrakort98twokbuqd 
        foreign key (device_id) 
        references Device (device_id);

    alter table Client 
        add constraint FK_cowu1nimxw9ilbpvwxwjof1qg 
        foreign key (category_id) 
        references Category (category_id);

    alter table Measure 
        add constraint FK_s6r4ccrgjqb1i1gwrc56m33f 
        foreign key (sensor_id) 
        references Sensor (sensor_id);

    alter table Residence 
        add constraint FK_g9f5caryapdu14l0l89wrx56f 
        foreign key (area_id) 
        references Area (area_id);

    alter table Residence 
        add constraint FK_g47mob20x3tths1xqyuy7h0ou 
        foreign key (client_id) 
        references Client (client_id);

    alter table Rule 
        add constraint FK_dq9oegjg8mw8pqrxbd2v2pj51 
        foreign key (actuator_id) 
        references Actuator (actuator_id);

    alter table Rule 
        add constraint FK_bfot57sbj6wxbqp977t11etrt 
        foreign key (sensor_id) 
        references Sensor (sensor_id);

    alter table Sensor 
        add constraint FK_ppmi5j5lawo8147qlw48a2pmd 
        foreign key (device_id) 
        references Device (device_id);

    alter table StateHistory 
        add constraint FK_jqo0nvgssmhbj4b6604l3yb1a 
        foreign key (device_id) 
        references Device (device_id);

    alter table TimeIntervalDevice 
        add constraint FK_lbfr0evtfbgib80598p843dh9 
        foreign key (device_id) 
        references Device (device_id);

    alter table Transformer 
        add constraint FK_2o1pg2dtnof1g1q7spbt8s9rh 
        foreign key (area_id) 
        references Area (area_id);
