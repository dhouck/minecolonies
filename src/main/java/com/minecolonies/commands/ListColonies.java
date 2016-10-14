package com.minecolonies.commands;

import com.minecolonies.colony.ColonyManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * List all colonies.
 */
public class ListColonies extends AbstractSingleCommand
{

    /**
     * Initialize this SubCommand with it's parents.
     *
     * @param parents an array of all the parents.
     */
    public ListColonies(@NotNull final String... parents)
    {
        super(parents);
    }

    @NotNull
    @Override
    public String getCommandUsage(@NotNull final ICommandSender sender)
    {
        return super.getCommandUsage(sender) + "";
    }

    private static final String ID_TEXT = "§2ID: §f";
    private static final String NAME_TEXT = "§2 Name: §f";
    private static final String COORDINATES_TEXT = "§8Coordinates: ";

    @Override
    public void execute(@NotNull final MinecraftServer server, @NotNull final ICommandSender sender, @NotNull final String... args) throws CommandException
    {
        int page = 1;
        final int colonyCount = ColonyManager.getColonies().size();
        int pageCount;
        final int coloniesOnPage = 8;

        if (colonyCount % coloniesOnPage == 0)
        {
            pageCount = colonyCount / coloniesOnPage;
        }
        else
        {
            pageCount = colonyCount / coloniesOnPage + 1;
        }
        if (args.length != 0)
        {
            try
            {
                page = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e)
            {
                //ignore and keep page 1.
            }
        }
        final TextComponentString headerLine = new TextComponentString("§2----------page " + page + " of " + pageCount + "----------");
        sender.addChatMessage(headerLine);
        final int lastColonyNumber = coloniesOnPage * page - (coloniesOnPage - 1);
        final int latestColonyNumber = coloniesOnPage * page;
        final int lastPageColonies = colonyCount % coloniesOnPage;
        if (page == pageCount)
        {
            if (coloniesOnPage == lastPageColonies)
            {
                for (int i = lastColonyNumber; i <= latestColonyNumber; i++)
                {
                    final TextComponentString colonyData =
                      new TextComponentString(ID_TEXT + ColonyManager.getColony(i).getID() + NAME_TEXT + ColonyManager.getColony(i).getName());
                    sender.addChatMessage(colonyData);
                    final TextComponentString colonyCoords = new TextComponentString(COORDINATES_TEXT + ColonyManager.getColony(i).getCenter());
                    sender.addChatMessage(colonyCoords);
                }
            }
            else
            {
                for (int i = lastColonyNumber; i <= latestColonyNumber - (coloniesOnPage - lastPageColonies); i++)
                {
                    final TextComponentString colonyData =
                      new TextComponentString(ID_TEXT + ColonyManager.getColony(i).getID() + NAME_TEXT + ColonyManager.getColony(i).getName());
                    sender.addChatMessage(colonyData);
                    final TextComponentString colonyCoords = new TextComponentString(COORDINATES_TEXT + ColonyManager.getColony(i).getCenter());
                    sender.addChatMessage(colonyCoords);
                }
            }
        }
        else
        {
            for (int i = lastColonyNumber; i <= latestColonyNumber; i++)
            {
                final TextComponentString colonyData = new TextComponentString(ID_TEXT + ColonyManager.getColony(i).getID()
                                                                                 + NAME_TEXT + ColonyManager.getColony(i).getName());
                sender.addChatMessage(colonyData);
                final TextComponentString colonyCoords = new TextComponentString(COORDINATES_TEXT + ColonyManager.getColony(i).getCenter());
                sender.addChatMessage(colonyCoords);
            }
        }
        final TextComponentString footerLine = new TextComponentString("§2------------------------------");
        sender.addChatMessage(footerLine);
    }

    @NotNull
    @Override
    public List<String> getTabCompletionOptions(
                                                 @NotNull final MinecraftServer server,
                                                 @NotNull final ICommandSender sender,
                                                 @NotNull final String[] args,
                                                 @Nullable final BlockPos pos)
    {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(@NotNull final String[] args, final int index)
    {
        return false;
    }
}
