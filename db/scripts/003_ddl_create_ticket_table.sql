CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT UTicket_Session_User UNIQUE (session_id, user_id),
    CONSTRAINT UTicket_Place UNIQUE (pos_row, cell)
);

COMMENT ON TABLE ticket IS 'Билеты. Каждый билет связывает пользователя с сеансом.';