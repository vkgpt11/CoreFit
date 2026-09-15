from __future__ import annotations

import os
from datetime import datetime
from sqlalchemy import Boolean, DateTime, Integer, String, Text, create_engine
from sqlalchemy.orm import DeclarativeBase, Mapped, mapped_column, sessionmaker

DATABASE_URL = os.getenv("DATABASE_URL", "sqlite:///./corefit.db")
connect_args = {"check_same_thread": False} if DATABASE_URL.startswith("sqlite") else {}
engine = create_engine(DATABASE_URL, pool_pre_ping=True, connect_args=connect_args)
SessionLocal = sessionmaker(bind=engine, autoflush=False, expire_on_commit=False)

class Base(DeclarativeBase):
    pass

class Exercise(Base):
    __tablename__ = "exercises"
    id: Mapped[str] = mapped_column(String(80), primary_key=True)
    name: Mapped[str] = mapped_column(String(160))
    category: Mapped[str] = mapped_column(String(80))
    description: Mapped[str] = mapped_column(Text, default="")
    reps: Mapped[int] = mapped_column(Integer, default=10)
    sets: Mapped[int] = mapped_column(Integer, default=1)
    move_seconds: Mapped[int] = mapped_column(Integer, default=2)
    hold_seconds: Mapped[int] = mapped_column(Integer, default=0)
    rest_seconds: Mapped[int] = mapped_column(Integer, default=20)
    published: Mapped[bool] = mapped_column(Boolean, default=False)
    media_url: Mapped[str | None] = mapped_column(Text, nullable=True)
    media_version: Mapped[int] = mapped_column(Integer, default=0)
    updated_at: Mapped[datetime] = mapped_column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)

def init_db() -> None:
    Base.metadata.create_all(engine)
